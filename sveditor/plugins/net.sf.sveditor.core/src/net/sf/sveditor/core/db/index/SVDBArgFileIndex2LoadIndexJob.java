package net.sf.sveditor.core.db.index;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;


public class SVDBArgFileIndex2LoadIndexJob {
	
	private Job						fJob;
	private SVDBArgFileIndex2		fIndex;

	public SVDBArgFileIndex2LoadIndexJob(SVDBArgFileIndex2 index) {
		fIndex = index;
	}
	
	public void load() {
		synchronized (this) {
			if (fJob == null) {
				fJob = new LoadIndexJob();
				fJob.schedule();
			} else {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class LoadIndexJob extends Job {
		public LoadIndexJob() {
			super("Load Index " + fIndex.getBaseLocation());
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				fIndex.loadIndex(monitor);
			} finally {
				synchronized (SVDBArgFileIndex2LoadIndexJob.this) {
					fJob = null;
					SVDBArgFileIndex2LoadIndexJob.this.notifyAll();
				}
			}
			
			return Status.OK_STATUS;
		}
	}
}

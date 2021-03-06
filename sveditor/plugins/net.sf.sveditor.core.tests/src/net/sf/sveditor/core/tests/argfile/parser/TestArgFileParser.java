package net.sf.sveditor.core.tests.argfile.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.sveditor.core.SVCorePlugin;
import net.sf.sveditor.core.argfile.parser.SVArgFileLexer;
import net.sf.sveditor.core.db.ISVDBItemBase;
import net.sf.sveditor.core.db.SVDBFile;
import net.sf.sveditor.core.db.SVDBItemType;
import net.sf.sveditor.core.db.SVDBMarker;
import net.sf.sveditor.core.db.argfile.SVDBArgFileDefineStmt;
import net.sf.sveditor.core.db.argfile.SVDBArgFileIncDirStmt;
import net.sf.sveditor.core.db.argfile.SVDBArgFileIncFileStmt;
import net.sf.sveditor.core.db.argfile.SVDBArgFileLibExtStmt;
import net.sf.sveditor.core.db.argfile.SVDBArgFileSrcLibPathStmt;
import net.sf.sveditor.core.db.argfile.SVDBArgFileStmt;
import net.sf.sveditor.core.log.LogFactory;
import net.sf.sveditor.core.log.LogHandle;
import net.sf.sveditor.core.parser.SVParseException;
import net.sf.sveditor.core.scanutils.ITextScanner;
import net.sf.sveditor.core.scanutils.StringTextScanner;
import net.sf.sveditor.core.tests.utils.TestUtils;

public class TestArgFileParser extends TestCase {
	private File				fTmpDir;
	
	@Override
	protected void setUp() throws Exception {
		fTmpDir = TestUtils.createTempDir();
	}

	@Override
	protected void tearDown() throws Exception {
		if (fTmpDir != null && fTmpDir.isDirectory()) {
			TestUtils.delete(fTmpDir);
		}
	}

	public void testOptionLexer() throws SVParseException {
		String testname = "testOptionLexer";
		LogHandle log = LogFactory.getLogHandle(testname);
		SVCorePlugin.getDefault().enableDebug(false);
		String content =
				"-f /tools/usr/dir/file.f\n" +
				"";
		ITextScanner scanner = new StringTextScanner(content);
		SVArgFileLexer lexer = new SVArgFileLexer();
		lexer.init(null, scanner);

		while (lexer.peek() != null) {
			log.debug("Token: \"" + lexer.getImage() + "\"");
			lexer.consumeToken();
		}
	}
	
	public void testStringArguments() throws SVParseException {
		String testname = "testOptionLexer";
		LogHandle log = LogFactory.getLogHandle(testname);
		SVCorePlugin.getDefault().enableDebug(false);
		String content =
				"\"This is a string\"\"and this is another\"\n" +
				"-f /tools/usr/dir/file.f\n" +
				"";
		ITextScanner scanner = new StringTextScanner(content);
		SVArgFileLexer lexer = new SVArgFileLexer();
		lexer.init(null, scanner);

		while (lexer.peek() != null) {
			log.debug("Token: \"" + lexer.getImage() + "\"");
			lexer.consumeToken();
		}

	}
	
	public void testPlusArgs() throws SVParseException {
		String testname = "testPlusArgs";
		LogHandle log = LogFactory.getLogHandle(testname);
		SVCorePlugin.getDefault().enableDebug(false);
		String content =
				"+define+foo=bar +my_plusarg=bar\n" +
				"";
		ITextScanner scanner = new StringTextScanner(content);
		SVArgFileLexer lexer = new SVArgFileLexer();
		lexer.init(null, scanner);

		while (lexer.peek() != null) {
			log.debug("Token: \"" + lexer.getImage() + "\"");
			lexer.consumeToken();
		}

	}

	public void testIncOpts() throws SVParseException {
		String testname = "testIncOpts";
		SVCorePlugin.getDefault().enableDebug(false);
		String content =
				"+incdir+/tools/include // Questa/VCS format\n" +
				"-Incdir /tools/include2 // NCSim format\n" +
				"";
		
		ArgFileParserTests.runParserTest(testname, content, new SVDBArgFileStmt[] {
				new SVDBArgFileIncDirStmt("/tools/include"),
				new SVDBArgFileIncDirStmt("/tools/include2")
		});
	}
	
	public void testDefOpts() throws SVParseException {
		String testname = "testDefOpts";
		SVCorePlugin.getDefault().enableDebug(false);
		String content =
				"+define+bar1=baz // Questa/VCS format\n" +
				"+define+foo1 // Questa/VCS format\n" +
				"-defi bar2=baz // NCSim format\n" +
				"-define foo2 // NCSim format\n" +
				"";
		
		ArgFileParserTests.runParserTest(testname, content, new SVDBArgFileStmt[] {
				new SVDBArgFileDefineStmt("bar1", "baz"),
				new SVDBArgFileDefineStmt("foo1", null),
				new SVDBArgFileDefineStmt("bar2", "baz"),
				new SVDBArgFileDefineStmt("foo2", null),
		});
	}

	public void testArgFileInc() throws SVParseException {
		String testname = "testArgFileInc";
		SVCorePlugin.getDefault().enableDebug(false);
		String content =
				"-f /tools/argfiles/argfile1.f\n" +
				"-file /tools/argfiles/argfile2.f\n" +
				"";
		
		ArgFileParserTests.runParserTest(testname, content, new SVDBArgFileStmt[] {
				new SVDBArgFileIncFileStmt("/tools/argfiles/argfile1.f"),
				new SVDBArgFileIncFileStmt("/tools/argfiles/argfile2.f")
		});
	}
	
	public void testLibExtOpts() throws SVParseException {
		String testname = getName();
		SVCorePlugin.getDefault().enableDebug(true);
		String content =
				"+libext+.v+.sv+.svh\n" +
				"-y foo\n" +
				"+incdir+bar\n" +
				"";
		
		ArgFileParserTests.runParserTest(testname, content, new SVDBArgFileStmt[] {
				new SVDBArgFileSrcLibPathStmt("foo"),
				new SVDBArgFileIncDirStmt("bar")
		});
	}	
	
	public void testLocations() throws SVParseException {
		SVCorePlugin.getDefault().enableDebug(false);
		LogHandle log = LogFactory.getLogHandle(getName());
		
		List<SVDBMarker> markers = new ArrayList<SVDBMarker>();
		
		String content =
				"\n" +									// 1
				"+incdir+/home/mballance\n" +			// 2
				"\n" +									// 3
				"\n" +									// 4
				"/home/mballance/class1.sv\n" +			// 5
				"/home/mballance/class2.sv\n" +			// 6
				"\n" +									// 7
				"\n" +									// 8
				"/home/mballance/class3.sv\n" +			// 9
				"\n";
				

		SVDBFile file = ArgFileParserTests.parse(log, null, getName(), content, markers);
	
		// Check line numbers
		int lineno[] = new int[] {2, 5, 6, 9};
		int idx = 0;
		for (ISVDBItemBase it : file.getChildren()) {
			assertTrue(idx < lineno.length);
			assertEquals("lineno for " + it.getType(), lineno[idx], it.getLocation().getLine());
		}
	}
}

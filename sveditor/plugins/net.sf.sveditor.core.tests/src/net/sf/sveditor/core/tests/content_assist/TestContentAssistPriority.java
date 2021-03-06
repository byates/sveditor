package net.sf.sveditor.core.tests.content_assist;

import net.sf.sveditor.core.SVCorePlugin;
import junit.framework.TestCase;

public class TestContentAssistPriority extends TestCase {
	
	public void testUntriggeredClassHierarchy() {
		SVCorePlugin.getDefault().enableDebug(false);
		String doc = 
			"class cls1;\n" +
			"  int			m_def;\n" +
			"  int			m_abc;\n" +
			"endclass\n" +
			"\n" +
			"class cls2 extends cls1;\n" +
			"  int			m_jkl;\n" +
			"  int			m_ghi;\n" +
			"endclass\n" +
			"\n" +
			"class cls3 extends cls2;\n" +
			"  int			m_pqr;\n" +
			"  int			m_mno;\n" +
			"\n" +
			"  function void foo;\n" +
			"    m_<<MARK>>\n" +
			"  endfunction\n" +
			"endclass\n"
			;
		
		ContentAssistTests.runTestOrder(getName(), doc, 
				"m_mno",
				"m_pqr",
				"m_ghi",
				"m_jkl",
				"m_abc",
				"m_def"
				);		
	}
	
	public void testTriggeredClassHierarchy() {
		SVCorePlugin.getDefault().enableDebug(false);
		String doc = 
			"class cls1;\n" +
			"  int			m_def;\n" +
			"  int			m_abc;\n" +
			"endclass\n" +
			"\n" +
			"class cls2 extends cls1;\n" +
			"  int			m_jkl;\n" +
			"  int			m_ghi;\n" +
			"endclass\n" +
			"\n" +
			"class cls3 extends cls2;\n" +
			"  int			m_pqr;\n" +
			"  int			m_mno;\n" +
			"\n" +
			"  function void foo;\n" +
			"    cls3 c;\n" +
			"    c.m_<<MARK>>\n" +
			"  endfunction\n" +
			"endclass\n"
			;
		
		ContentAssistTests.runTestOrder(getName(), doc, 
				"m_mno",
				"m_pqr",
				"m_ghi",
				"m_jkl",
				"m_abc",
				"m_def"
				);
	}

	public void testLocalScopeVars() {
		SVCorePlugin.getDefault().enableDebug(false);
		String doc = 
			"class cls1;\n" +
			"  int			m_def;\n" +
			"  int			m_abc;\n" +
			"endclass\n" +
			"\n" +
			"class cls2 extends cls1;\n" +
			"  int			m_jkl;\n" +
			"  int			m_ghi;\n" +
			"endclass\n" +
			"\n" +
			"class cls3 extends cls2;\n" +
			"  int			m_pqr;\n" +
			"  int			m_mno;\n" +
			"\n" +
			"  function void foo;\n" +
			"    int m_pqr;\n" +
			"    begin\n" +
			"      int stu;\n" +
			"		\n" +
			"      m_<<MARK>>\n" +
			"    end\n" +
			"  endfunction\n" +
			"endclass\n"
			;
		
		ContentAssistTests.runTestOrder(getName(), doc, 
				"m_stu",
				"m_pqr",
				"m_mno",
				"m_pqr",
				"m_ghi",
				"m_jkl",
				"m_abc",
				"m_def"
				);		
	}
	
	public void testFieldClassOrdering() {
		SVCorePlugin.getDefault().enableDebug(false);
		String doc = 
			"class m_cls2;\n" +
			"endclass\n" +
			"\n" +
			"class m_cls1;\n" +
			"endclass\n" +
			"\n" +
			"class cls1;\n" +
			"  int			m_def;\n" +
			"  int			m_abc;\n" +
			"endclass\n" +
			"\n" +
			"class cls2 extends cls1;\n" +
			"  int			m_jkl;\n" +
			"  int			m_ghi;\n" +
			"endclass\n" +
			"\n" +
			"class cls3 extends cls2;\n" +
			"  int			m_pqr;\n" +
			"  int			m_mno;\n" +
			"\n" +
			"  function void foo;\n" +
			"    int m_pqr;\n" +
			"    m_<<MARK>>\n" +
			"  endfunction\n" +
			"endclass\n"
			;
		
		ContentAssistTests.runTestOrder(getName(), doc, 
				"m_pqr",
				"m_mno",
				"m_ghi",
				"m_jkl",
				"m_abc",
				"m_def",
				"m_cls1",
				"m_cls2"
				);		
	}	
}

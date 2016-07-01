package org.jetbrains.postfixCompletion.templates;

import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TemplateManagerImpl;
import com.intellij.codeInsight.template.impl.TemplateState;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;

public class InstanceofPostfixTemplateTest extends PostfixTemplateTestCase {
  @Override
  protected String getTestDataPath() { return "testData/templates/instanceof"; }

  public void testSingleExpression()  { doTest(); }
  public void testAlias()             { doTest(); }

  public void testSingleExpressionTemplate() {
    final TemplateManagerImpl templateManager = (TemplateManagerImpl) TemplateManager.getInstance(getProject());
    templateManager.setTemplateTesting(true);
    Disposer.register(getTestRootDisposable(), new Disposable() {
      @Override
      public void dispose() {
        templateManager.setTemplateTesting(false);
      }
    });


    myFixture.configureByFile(getTestName(true) + ".java");
    myFixture.type('\t');

    TemplateState templateState = TemplateManagerImpl.getTemplateState(myFixture.getEditor());
    assertNotNull(templateState);
    assertFalse(templateState.isFinished());

    myFixture.type("Integer");
    templateState.nextTab();
    assertTrue(templateState.isFinished());

    myFixture.checkResultByFile(getTestName(true) + "_after.java", true);
  }
}

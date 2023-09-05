package translationworkflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import translationworkflow.model.TranslationWorkflowInput;
import translationworkflow.model.TranslationWorkflowOutput;

@WorkflowInterface
public interface TranslationWorkflow {

  @WorkflowMethod
  // TODO: Update the method header to take a single input
  // of type TranslationWorkflowInput as a parameter and
  // return TranslationWorkflowOutput
  String sayHelloGoodbye(String term, String languageCode);

}

package translationworkflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import translationworkflow.model.TranslationActivityInput;
import translationworkflow.model.TranslationActivityOutput;
import translationworkflow.model.TranslationWorkflowInput;
import translationworkflow.model.TranslationWorkflowOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class TranslationWorkflowImpl implements TranslationWorkflow {

  private static final Logger logger = LoggerFactory.getLogger(TranslationWorkflowImpl.class);

  private final ActivityOptions options = ActivityOptions.newBuilder()
      .setStartToCloseTimeout(Duration.ofSeconds(5))
      .build();

  private final TranslationActivities activities = Workflow.newActivityStub(TranslationActivities.class, options);

  @Override
  public TranslationWorkflowOutput sayHelloGoodbye(TranslationWorkflowInput input) {
    String name = input.getName();
    String languageCode = input.getLanguageCode();

    logger.info("Workflow has been invoked. Name: {}, Language Code: {}", name, languageCode);

    TranslationActivityInput helloInput = new TranslationActivityInput("hello", languageCode);
    logger.debug("[ACTIVITY INVOKED] Translation Activity is going to be invoked. Message to be translated: {}, Language Code: {}",
        helloInput.getTerm(), languageCode);
    TranslationActivityOutput helloResult = activities.translateTerm(helloInput);
    String helloMessage = helloResult.getTranslation() + ", " + name;

    // Wait a little while before saying goodbye
    logger.info("Sleeping between translation calls");
    Workflow.sleep(Duration.ofSeconds(30));


    TranslationActivityInput goodbyeInput = new TranslationActivityInput("goodbye", languageCode);
    logger.debug("[ACTIVITY INVOKED] Translation Activity is going to be invoked. Message to be translated: {}, Language Code: {}",
        goodbyeInput.getTerm(), languageCode);
    TranslationActivityOutput goodbyeResult = activities.translateTerm(goodbyeInput);
    String goodbyeMessage = goodbyeResult.getTranslation() + ", " + name;

    logger.debug("[ACTIVITY COMPLETED] Translation Activity completed. Translated hello: {}, goodbye: {}",
        helloMessage, goodbyeMessage);
    return new TranslationWorkflowOutput(helloMessage, goodbyeMessage);
  }
}

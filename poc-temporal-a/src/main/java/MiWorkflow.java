import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;

@WorkflowInterface
public interface MiWorkflow {
    @SignalMethod
    void terminarWorkflow(String signalContent);
}

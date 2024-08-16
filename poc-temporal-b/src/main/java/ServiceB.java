import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

// Servicio B: Configuración del Worker y ejecución del Workflow
public class ServiceB {

    public static void main(String[] args) {
        // Conectar con el servidor de Temporal
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget("127.0.0.1:7233")  // Asegúrate de que esta dirección es la correcta para tu servidor
                        .build());
        WorkflowClient client = WorkflowClient.newInstance(service, WorkflowClientOptions.newBuilder()
                .setNamespace("default")  // Asegúrate de que el namespace es correcto
                .build());

        // Configurar el Worker para escuchar señales en ServiceBTaskQueue
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("serviceBTaskQueue");
        worker.registerWorkflowImplementationTypes(MyWorkflowImpl.class);

        // Iniciar el Worker
        factory.start();

        String workflowId = "serviceBWorkflowId2";
        // Iniciar el Workflow
        MyWorkflow workflow = client.newWorkflowStub(MyWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("serviceBTaskQueue")
                        .setWorkflowId(workflowId)
                        .build());

        WorkflowClient.start(workflow::startWorkflow);

        System.out.println("Service B is running and waiting for signals...");
    }
}

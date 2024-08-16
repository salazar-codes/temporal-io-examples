import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

public class ServiceA {

    public static void main(String[] args) {
        // Conectar con el servidor de Temporal en el puerto correcto para gRPC
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget("127.0.0.1:7233")  // Cambiado para apuntar al puerto gRPC de Temporal
                        .build());

        // Crear un cliente de Temporal con el namespace correcto
        WorkflowClient client = WorkflowClient.newInstance(service,
                WorkflowClientOptions.newBuilder()
                        .setNamespace("default")  // Asegúrate de que este es el namespace correcto
                        .build());

        // Definir el Workflow ID - debe ser exactamente igual al usado en el Servicio B
        String workflowId = "serviceBWorkflowId2";  // Ajusta el ID si es necesario
        String taskQueue = "serviceBTaskQueue";


        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setTaskQueue(taskQueue)
                .build();

        try {
            WorkflowStub untypedWorkflowStub = client.newUntypedWorkflowStub(workflowId);

            // Enviar la señal al Workflow
            untypedWorkflowStub.signal("receiveSignal", "Hello from Service A");

            System.out.println("Signal sent from Service A to Service B.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send signal: " + e.getMessage());
        }

        // System.out.println("Signal sent from Service A to Service B.");
    }
}

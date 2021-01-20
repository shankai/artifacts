package me.kvn.codes.service.client;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import me.kvn.codes.service.Status;
import me.kvn.codes.service.Vehicle;
import me.kvn.codes.service.VehicleServiceGrpc;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleClient {
    private static final Logger logger = Logger.getLogger(VehicleClient.class.getName());

    private final VehicleServiceGrpc.VehicleServiceBlockingStub blockingStub;

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    public VehicleClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = VehicleServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Say hello to server.
     */
    public void vehicleRun(String id) {
        logger.info("### Ready running vehicle: " + id);
        Vehicle vehicle = Vehicle.newBuilder().setId(id).build();
        Status status;

        try {
            status = blockingStub.running(vehicle);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("### " + id + " Running Status: " + status.getStatus());
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting. The second argument is the target server.
     */
    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        String target = "localhost:50051";

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        try {
            VehicleClient client = new VehicleClient(channel);
            client.vehicleRun("x5");
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}

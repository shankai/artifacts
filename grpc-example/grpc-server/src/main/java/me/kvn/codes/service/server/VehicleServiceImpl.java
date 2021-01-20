package me.kvn.codes.service.server;

import io.grpc.stub.StreamObserver;
import me.kvn.codes.service.Status;
import me.kvn.codes.service.Vehicle;
import me.kvn.codes.service.VehicleServiceGrpc;

import java.util.logging.Logger;

public class VehicleServiceImpl extends VehicleServiceGrpc.VehicleServiceImplBase {
    private static final Logger logger = Logger.getLogger(VehicleServiceImpl.class.getName());

    @Override
    public void running(Vehicle request, StreamObserver<Status> responseObserver) {

        Status.Builder builder = Status.newBuilder();
        builder.setVehicleId(request.getId());
        builder.setStatus(true);

        logger.info("=== Running Vehicle Id : " + request.getId());

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

//        super.running(request, responseObserver);
    }
}

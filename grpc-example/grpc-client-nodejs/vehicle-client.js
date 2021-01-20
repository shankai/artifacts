var PROTO_PATH = __dirname + '/../grpc-service/src/main/proto/vehicle_service.proto';

var parseArgs = require('minimist');
var grpc = require('@grpc/grpc-js');
var protoLoader = require('@grpc/proto-loader');
var packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    });

var definition = grpc.loadPackageDefinition(packageDefinition);

function main() {
//console.log(definition);
var vehicleService = grpc.loadPackageDefinition(packageDefinition).VehicleService;
//  console.log(vehicleService);
  var target = 'localhost:50051';
  var client = new vehicleService(target, grpc.credentials.createInsecure());

  client.running({id: 'vehicle-X5-node'}, function(err, response) {
      if(err != null) {
          console.error('Response Error:' , err)
      } else {
          console.log('Node Status: ', response);
      }
  });
}
//
main();

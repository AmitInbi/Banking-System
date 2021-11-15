
import py4j.GatewayServer;


public class py4j_EntryPoint {

    private API api;


    public py4j_EntryPoint(){
        this.api = new API();
    }

    // Provide access to pre-configured Banking System
    public API getAPI(){
        return this.api;
    }

    public static void main(String[] args){
        // initialize a GatewayServer and link it to an entry point
        GatewayServer gatewayServer = new GatewayServer(new py4j_EntryPoint());
        // start the gateway so it can accept incoming Python requests:
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }
}

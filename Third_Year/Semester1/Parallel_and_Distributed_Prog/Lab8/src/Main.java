import message.CloseMessage;
import message.Message;
import message.SubscribeMessage;
import message.UpdateMessage;
import mpi.MPI;

public class Main {

    private static class Listener implements Runnable {

        private final DSM dsm;

        public Listener(DSM dsm) {
            this.dsm = dsm;
        }



        @Override
        public void run() {
            while (true) {
                System.out.println("RANK " + MPI.COMM_WORLD.Rank() + " waiting...");
                Object[] messages = new Object[1];

                MPI.COMM_WORLD.Recv(messages, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, MPI.ANY_TAG);
                Message message = (Message) messages[0];

                if (message instanceof CloseMessage) {
                    System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " stopped listening...");
                    return;
                } else if (message instanceof SubscribeMessage) {
                    SubscribeMessage subscribeMessage = (SubscribeMessage) message;
                    System.out.println("Subscribe message received");
                    System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " received: rank " + subscribeMessage.rank + " subscribes to " + subscribeMessage.var);
                    dsm.syncSubscription(subscribeMessage.var, subscribeMessage.rank);
                } else if (message instanceof UpdateMessage) {
                    UpdateMessage updateMessage = (UpdateMessage) message;
                    System.out.println("Update message received");
                    System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " received:" + updateMessage.var + "->" + updateMessage.val);
                    dsm.setVariable(updateMessage.var, updateMessage.val);
                }

                writeAll(dsm);
            }
        }
    }

    public static void writeAll(DSM dsm) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Write all\n");
        stringBuilder.append("Rank ").append(MPI.COMM_WORLD.Rank()).append(" -> a = ").append(dsm.a).append(" b = ").append(dsm.b).append(" c = ").append(dsm.c).append(" \n");
        stringBuilder.append("Subscribers: \n");
        for (String var : dsm.subscribers.keySet()) {
            stringBuilder.append(var).append("->").append(dsm.subscribers.get(var).toString()).append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        MPI.Init(args);
        
        DSM dsm = new DSM();
        int me = MPI.COMM_WORLD.Rank();
        
        if (me == 0) {
            Thread thread = new Thread(new Listener(dsm));
            
            thread.start();
            
            dsm.subcribeTo("a");
            dsm.subcribeTo("b");
            dsm.subcribeTo("c");
            dsm.checkAndReplace("a", 0, 111);
            dsm.checkAndReplace("c", 2, 333);
            dsm.checkAndReplace("b", 1, 101);
            Thread.sleep(1000);
            dsm.close();
            
            thread.join();
        } else if (me == 1) {
            Thread thread = new Thread(new Listener(dsm));
            thread.start();

            dsm.subcribeTo("a");
            dsm.subcribeTo("c");
            Thread.sleep(100);
            thread.join();
        } else if (me == 2) {
            Thread thread = new Thread(new Listener(dsm));

            thread.start();

            dsm.subcribeTo("b");
            dsm.checkAndReplace("b", 101, 100);
            Thread.sleep(100);
            thread.join();
        }

        MPI.Finalize();
    }
}
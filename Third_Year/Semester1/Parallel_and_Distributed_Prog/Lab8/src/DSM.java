import message.CloseMessage;
import message.Message;
import message.SubscribeMessage;
import message.UpdateMessage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import mpi.MPI;

public class DSM {

    public Map<String, Set<Integer>> subscribers;
    public int a;
    public int b;
    public int c;
    public static final Lock lock = new ReentrantLock();

    public DSM() {
        this.a = 0;
        this.b = 1;
        this.c = 2;
        subscribers = new ConcurrentHashMap<>();
        subscribers.put("a", new HashSet<>());
        subscribers.put("b", new HashSet<>());
        subscribers.put("c", new HashSet<>());
    }

    public void updateVariable(String var, int value) {
        lock.lock();

        setVariable(var, value);
        Message message = new UpdateMessage(var, value);
        sendToSubscribers(var, message);

        lock.unlock();
    }

    public void setVariable(String var, int value) {
        switch (var) {
            case "a":
                this.a = value;
                break;
            case "b":
                this.b = value;
                break;
            case "c":
                this.c = value;
                break;
            default:
                break;
        }
    }

    public void checkAndReplace(String var, int old, int newVal) {
        if (var.equals("a") && this.a == old) {
            updateVariable("a", newVal);
        }
        else if (var.equals("b") && this.b == old) {
            updateVariable("b", newVal);
        }
        else {
            updateVariable("c", newVal);
        }
    }

    public void subcribeTo(String var) {
        Set<Integer> subs = this.subscribers.get(var);
        subs.add(MPI.COMM_WORLD.Rank());
        this.subscribers.put(var, subs);
        this.sendAll(new SubscribeMessage(var, MPI.COMM_WORLD.Rank()));
    }

    public void syncSubscription(String var, int rank) {
        Set<Integer> subs = subscribers.get(var);
        subs.add(rank);
        subscribers.put(var, subs);
    }

    public void sendToSubscribers(String var, Message msg) {
        for (int i = 0; i < MPI.COMM_WORLD.Size(); i++) {
            if (MPI.COMM_WORLD.Rank() == i || !subscribers.get(var).contains(i))
                continue;

            MPI.COMM_WORLD.Send(new Object[]{msg}, 0, 1, MPI.OBJECT, i, 0);
        }
    }

    private void sendAll(Message message) {
        for (int i = 0; i < MPI.COMM_WORLD.Size(); i++) {
            if (MPI.COMM_WORLD.Rank() == i && !(message instanceof CloseMessage))
                continue;
            MPI.COMM_WORLD.Send(new Object[]{message}, 0, 1, MPI.OBJECT, i, 0);
        }
    }

    public void close() {
        this.sendAll(new CloseMessage());
    }


}

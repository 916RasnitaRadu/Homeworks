import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HamiltonianCycleSearchTask implements Runnable {

    private final List<List<Integer>> graph;
    private final int startNode;
    private final AtomicBoolean foundHamiltonianCycle;
    private final List<Integer> possiblePath;
    private final List<Integer> result;
    private final Lock lock;
    private final List<Boolean> visited;

    public HamiltonianCycleSearchTask(List<List<Integer>> graph, int startNode, AtomicBoolean foundCycle, List<Integer> result) {
        this.graph = graph;
        this.startNode = startNode;
        this.foundHamiltonianCycle = foundCycle;
        this.result = result;
        this.visited = new ArrayList<>();
        this.possiblePath = new ArrayList<>();
        this.lock = new ReentrantLock();

        for (int i = 0; i < this.graph.size(); i++)
        {
            visited.add(false);
        }
    }

    private void foundCycle() {
        this.possiblePath.add(startNode);
        this.foundHamiltonianCycle.set(true);

        this.lock.lock();

        this.result.clear();
        this.result.addAll(possiblePath);

        this.lock.unlock();
    }

    private void dfs(int nextNode) {
        // Base case - we found a solution
        if (foundHamiltonianCycle.get()) { return; }

        this.possiblePath.add(nextNode);
        if (this.possiblePath.size() == this.graph.size()) {
            if (this.graph.get(nextNode).contains(this.startNode)) {
                this.foundCycle();
            }
        }
        else {
            for (Integer outboundNeighbor : this.graph.get(nextNode)) {
                if (!this.visited.get(outboundNeighbor)) {
                    this.dfs(outboundNeighbor);
                    return;
                }
            }
        }
    }

    @Override
    public void run() {
        dfs(startNode);
    }
}

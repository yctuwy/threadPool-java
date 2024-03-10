package org.example.mypool;

@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(TaskQueue queue,T task);
}

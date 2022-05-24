package com.salesman.Servises.antAlgorhytm;

import lombok.Data;

@Data
public class Ant
{
    protected int trailSize;
    protected int trail[];
    protected boolean visit[];

    public Ant(int tourSize)
    {
        this.trailSize = tourSize;
        this.trail = new int[tourSize];
        this.visit = new boolean[tourSize];
    }

    protected void visitToCity(int currentIndex, int city)
    {
        trail[currentIndex + 1] = city; //добавить в след
        visit[city] = true;           //обновить флаг
    }

    protected boolean visited(int i)
    {
        return visit[i];
    }

    protected double trailLength(double graph[][])
    {
        double length = graph[trail[trailSize - 1]][trail[0]];
        for (int i = 0; i < trailSize - 1; i++)
            length += graph[trail[i]][trail[i + 1]];
        return length;
    }

    protected void clear()
    {
        for (int i = 0; i < trailSize; i++)
            visit[i] = false;
    }
}
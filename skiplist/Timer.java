/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandatoryproject2;

/**
 *
 * @author Srabonti Chakraborty SXC154030
 */
public class Timer {
    long startTime, endTime, elapsedTime, memAvailable, memUsed;

    Timer() {
	startTime = System.currentTimeMillis();
    }

    public void start() {
	startTime = System.currentTimeMillis();
    }

    public Timer end() {
	endTime = System.currentTimeMillis();
	elapsedTime = endTime-startTime;
	memAvailable = Runtime.getRuntime().totalMemory();
	memUsed = memAvailable - Runtime.getRuntime().freeMemory();
	return this;
    }

    public String toString() {
        return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
    }
}

var Fork = function () {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function (cb) {
    const fork = this;
    let bebAlgorithm = function (time) {
        setTimeout(function () {
            if (fork.state === 0) {
                fork.state = 1;
                cb();
            } else {
                if (time < 1024) {
                    bebAlgorithm(time * 2);
                } else {
                    bebAlgorithm(time);
                }
            }
        }, time);
    };

    bebAlgorithm(1);
}

Fork.prototype.release = function () {
    this.state = 0;
}

var Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    this.eatingCount = 0;
    this.waitStart = 0;
    this.waitTime = 0;
    return this;
}

Philosopher.prototype.startNaive = function (count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        philosopher = this;

    let loopNaive = function (count) {
        setTimeout(function () {
            if (count > 0) {
                philosopher.waitStart = new Date().getTime();
                forks[f1].acquire(function () {
                    forks[f2].acquire(function () {
                        philosopher.waitTime += new Date().getTime() - philosopher.waitStart;
                        philosopher.eatingCount++;
                        setTimeout(function () {
                            forks[f1].release();
                            forks[f2].release();
                            loopNaive(count - 1);
                        }, Math.floor(Math.random() * 10))
                    })
                })
            } else {
                philosophersRunningCount--;
                if (philosophersRunningCount === 0) {
                    printAvgTimes();
                }
            }
        }, Math.floor(Math.random() * 100));  // More diverse and generally longer wait time for thinking
    }

    loopNaive(count);
}

Philosopher.prototype.startAsym = function (count) {
    let forks = this.forks,
        id = this.id,
        f1 = id % 2 === 1 ? this.f2 : this.f1,
        f2 = id % 2 === 1 ? this.f1 : this.f2,
        philosopher = this;

    let loopAsym = function (count) {
        setTimeout(function () {
            if (count > 0) {
                philosopher.waitStart = new Date().getTime();
                forks[f1].acquire(function () {
                    forks[f2].acquire(function () {
                        philosopher.waitTime += new Date().getTime() - philosopher.waitStart;
                        philosopher.eatingCount++;
                        setTimeout(function () {
                            forks[f1].release();
                            forks[f2].release();
                            loopAsym(count - 1);
                        }, Math.floor(Math.random() * 10))
                    })
                })
            } else {
                philosophersRunningCount--;
                if (philosophersRunningCount === 0) {
                    printAvgTimes();
                }
            }
        }, Math.floor(Math.random() * 100));  // More diverse and generally longer wait time for thinking
    }

    loopAsym(count);
}

var Conductor = function (philosophersNumber) {
    this.accessesNumber = philosophersNumber - 1;
    return this;
}

Conductor.prototype.acquire = function (cb) {
    const conductor = this;
    let bebAlgorithmConductor = function (time) {
        setTimeout(function () {
            if (conductor.accessesNumber > 0) {
                conductor.accessesNumber--;
                cb();
            } else {
                if (time < 1024) {
                    bebAlgorithmConductor(time * 2);
                } else {
                    bebAlgorithmConductor(time);
                }
            }
        }, time);
    };

    bebAlgorithmConductor(1);
}

Conductor.prototype.release = function () {
    this.accessesNumber++;
}

Philosopher.prototype.startConductor = function (count, conductor) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        philosopher = this;

    let loopConductor = function (count) {
        setTimeout(function () {
            if (count > 0) {
                philosopher.waitStart = new Date().getTime();
                conductor.acquire(function () {
                    forks[f1].acquire(function () {
                        forks[f2].acquire(function () {
                            philosopher.waitTime += new Date().getTime() - philosopher.waitStart;
                            philosopher.eatingCount++;
                            setTimeout(function () {
                                forks[f1].release();
                                forks[f2].release();
                                conductor.release();
                                loopConductor(count - 1);
                            }, Math.floor(Math.random() * 10))
                        })
                    })
                })
            } else {
                philosophersRunningCount--;
                if (philosophersRunningCount === 0) {
                    printAvgTimes();
                }
            }
        }, Math.floor(Math.random() * 100));  // More diverse and generally longer wait time for thinking
    }

    loopConductor(count);
}

Philosopher.prototype.acquireBoth = function (cb) {
    const fork1 = this.forks[this.f1], fork2 = this.forks[this.f2];

    let bebAlgorithm = function (time) {
        setTimeout(function () {
            if (fork1.state === 0) {
                fork1.state = 1;
                if (fork2.state === 0) {
                    fork2.state = 1;
                    cb();
                } else {
                    fork1.state = 0;
                    if (time < 4096) {
                        bebAlgorithm(time * 2);
                    } else {
                        bebAlgorithm(time);
                    }
                }
            } else {
                if (time < 4096) {
                    bebAlgorithm(time * 2);
                } else {
                    bebAlgorithm(time);
                }
            }
        }, time);
    };

    bebAlgorithm(1);
}

Philosopher.prototype.startBothForks = function (count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        philosopher = this;

    let loopPickupBoth = function (count) {
        setTimeout(function () {
            if (count > 0) {
                philosopher.waitStart = new Date().getTime();
                philosopher.acquireBoth(function () {
                    philosopher.waitTime += new Date().getTime() - philosopher.waitStart;
                    philosopher.eatingCount++;
                    setTimeout(function () {
                        forks[f1].release();
                        forks[f2].release();
                        loopPickupBoth(count - 1);
                    }, Math.floor(Math.random() * 10))
                })
            } else {
                philosophersRunningCount--;
                if (philosophersRunningCount === 0) {
                    printAvgTimes();
                }
            }
        }, Math.floor(Math.random() * 100));  // More diverse and generally longer wait time for thinking
    }

    loopPickupBoth(count);
}

function printAvgTimes() {
    // console.log("PARAMS: philosophersNumber: " + n + ", iterations: " + iterations + ", version: " + methodName + "\n");
    console.log(methodName + "-" + n + "-" + iterations + "-js");
    for (var i = 0; i < n; i++) {
        console.log("PHILOSOPHER ID: " + philosophers[i].id + "\tAVG WAIT TIME: " + philosophers[i].waitTime / iterations);
    }
}

const args = process.argv.slice(2);
var n = parseInt(args[0]);
var iterations = parseInt(args[1]);
var methodName = args[2];
var forks = [];
var philosophers = []
let conductor = new Conductor(n)

for (var i = 0; i < n; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < n; i++) {
    philosophers.push(new Philosopher(i, forks));
}
let philosophersRunningCount = n;
for (var i = 0; i < n; i++) {
    if (methodName === "naive") philosophers[i].startNaive(iterations);
    else if (methodName === 'asym') philosophers[i].startAsym(iterations);
    else if (methodName === 'bothForks') philosophers[i].startBothForks(iterations);
    else if (methodName === 'arbiter') philosophers[i].startConductor(iterations, conductor);
}


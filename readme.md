# BPlag

BPlag is a behavioural academic source code plagiarism detection tool. It represents a set of assignment submissions in a graph-based format representing the submissions' dynamic execution behaviours. It then compares these graphs to measure behavioural similarity. A high similarity is an indication of plagiarism.

## Usage

BPlag is a three-stage pipeline. First the execution behaviour is recorded as a set of execution traces. Secondly, the traces are converted to represent behaviour as a graph. Thirdly, the graphs are compared for similarity.

These instructions assume a unix-like environment.

### 1. Execution Trace Extraction

```
java -cp .:bplag-0.1.jar bplag.TraceGenerator <path-to-config-file>
```

The configuration file is a JSON document structured as followed:
```
{
    "title": "name of the assignment being analysed",
    "projectsRoot": "relative path to the assignment submissions",
    "output": "output path to store the execution traces",
    
    "globalLibraries": {
        "relative-path-to-lib1.jar",
        "relative-path-to-lib2.jar"
    },
    "projectLibraries": {
        "submission1": {
            "relative-path-to-submission1-libraryA.jar"        
        }
    },

    "maxRecursiveCalls": 2,
    "maxLoopExecutions": 3
}
```

All submissions must be in the specified `projectsRoot` directory. They all must being in their own labelled folder, containing all required sources.

As BPlag simulates the execution of a program, it must have access to all Java class dependencies. By default, no dependencies are provided. Hence, you need to list **AT LEAST** the rt.jar file (found in your Java 8 installation directory).

Submission-specific libraries can be specified in `projectLibraries`. 

### 2. Graph Processing

```
java -cp .:bplag-0.1.jar bplag.GraphProcessor <input-path-to-traces> <output-path-to-graphs>
```

The `path-to-traces` path corresponds to the output of step 1. The `path-to-graphs` path is the output (a set of serialised graphs representing the program behaviours).

### 3. Graph Comparator

```
java -cp .:bplag-0.1.jar bplag.GraphComparator <path-to-graphs>
```

`path-to-graphs` corresponds to the output from step 2. BPlag will compare all submissions pairwise.

## Limitations

BPlag is a proof-of-concept, and as a result, it may fail to execute some code. It only supports running on Oracle Java 8 - higher versions may fail. In particular, it does not support all Java features, namely, Anonymous classes, non-static inner classes and lambdas/method references. However, it is liable to fail on other code. 

Furthermore, BPlag is computationally complex, requires lots of RAM and disk space, and does not scale to large data set sizes. It should not be used on conventional computers - HPC workstations only.

## Disclaimer

Use this code at your own risks. Permission is granted for reasonable academic research reuse. Commercial use is explicitly forbidden.
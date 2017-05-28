# Multi-Agent Simulation - timingagents

This project timingagents contains a full working scenario of [LightJason](http://lightjason.org) multi-agent framework. The scenario description is:


The packages is _automatically generated_.

## Usage

The jar file can be executed directly, with

```
java -jar timingagents-0.0.1-SNAPSHOT.jar
```

### Help

For any help information the option ```-help``` can be set and shows additional information

```
java -jar timingagents-0.0.1-SNAPSHOT.jar -help
```

### ASL Files Generating

For generating agents (ASL files) the jar can be executed with

```
java -jar timingagents-0.0.1-SNAPSHOT.jar -create
```

### Simulation Example

First run the ASL generating, after that run the simulation with 

```
java -jar timingagents-0.0.1-SNAPSHOT.jar -asl DefaultAgent.asl -agents 2 -generator default -steps 5
```

it creates 2 agents based on the ASL script ```DefaultAgent.asl``` with the generator ```default``` and runs the simulation 5 steps.

## Agents

The following agents are generated 

 * ```DefaultAgent.asl``` 

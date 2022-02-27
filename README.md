# coffee-shop

This repo contains a simple toy project written in Clojure. The goal is to simulate the day-to-day business operations of a coffee shop—operations such as hiring and paying staff, receiving and filling orders from customers, and managing finances. 

In its current sub-1.0 status, this repo contains building blocks for such a simulation, but most of them do not directly contribute to the simulation yet.

## Development

Clone this repo using either git or the GitHub CLI:

```
$ git clone https://github.com/CFiggers/coffee-shop
```

or 

```
$ gh repo clone CFiggers/coffee-shop
```

I'm using Leiningen as my build and dependency management tool. Run `lein deps` from the project's root folder to make sure you have all the required dependencies.

## Usage

As of this writing, it is possible to run an extremely crude form of the coffee shop simulation using Leiningen. 

```
$ lein run arg1 arg2 
```

`arg1` should be a number between 1 and 3 indicating the "difficulty" of the simulation.

`arg2` should be any number indiciating the number of "days" the simulation should run.

The return will be a daily readout of the running funds of the coffee shop and then at the end a nested map showing some randomly generated numbers of ":coffees" made during that time.

```
$ lein run 1 2
You have $2500 left
You have $2680 left
{1 {:morning {:coffees 81}, :afternoon {:coffees 44}, :evening {:coffees 11}}, 2 {:morning {:coffees 110}, :afternoon {:coffees 47}, :evening {:coffees 14}}}
```

Eventually, this will give a much more satisfying output and may even become some sort of interactive simulator game using a CLI tool like [cli4clj](https://github.com/ruedigergad/cli4clj).

## Contribution

Issues and pull requests are welcome, but this is mostly a project for my own benefit.

## Future Plans

At some point, I'd like this to be a fully interactive coffee shop simulation allowing some player input on things like staffing, market positioning, and shop upgrades. I'll keep tinkering with it as long as it's interesting and is still helping me learn Clojure.

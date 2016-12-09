# Task management

Tasks are managed as [GitHub issues](https://github.com/Zuehlke/HouseOfCards/issues). Additionally, we use
[Waffle.io](https://waffle.io/Zuehlke/HouseOfCards) in order to ease the Scrum planning.
GitHub commits should usually be linked to the corresponding issue. If a commit resolves an issue, make it clear and
automatically close the issue by adding the following to the commit message:
> resolves #issueNo

where issueNo is the id of the issue and resolves could be replaced by any of the issue-closing keywords (as defined by GitHub).

# Labels
There are four categories of labels: task type, system area, issue type, and status.

## Task Type (orange)
Task types represent the different kinds of work. They help in structuring and prioritizing tasks.

These are the different task types:
* **bug**
* **documentation**
  * maintaining this Wiki (currently the central place for documentation) and code documentation
* **enhancement**
  * generally speaking, enhancement stands for optimizations representing NFRs
* **feature**
  * generally speaking, features are implementations of FRs
* **refactoring**
  * refactorings don't change the behavior of the system, they simply make the code or project cleaner
* **requirement**
  * requirement analysis
* **testing**

## System Area (blue)
System area labels are used mostly to make clear which part of the system / team is responsible for the particular task.
These tags usually represent a module of the system (see [Project Structure](project_structure.md)):
* **bot-domain**
* **bot-server**
* **game-domain**
* **game-server**
* **game-sever-rest**
* **team**
  * module that is responsible for user and team management (doesn't yet exist as of today)

## Issue Type (white)
When a GitHub issue doesn't represent a task, it is assigned an issue type:
* **discussion**
  * if the issue itself is a discussion and not a task (yet)
* **duplicate**
* **question**
* **suggestion**
* **wontfix**
  * an issue or a suggestion that won't make it to the backlog

## Status (yellow)
Status labels are used mostly for Waffle.io.
There are only two status labels but four different statuses. This is explained in detail below.
* **in progress**
* **ready**

# Waffle.io
Waffle.io basically just provides a more practical representation of the GitHub issues.
Its main features are:
* prioritize issues
* assign estimated cost to issue
* overview over issues divided by status

## Statuses
There are four columns in our Waffle.io board—each representing a status:
* Backlog
  * issues that aren't assigned the _ready_, nor the _in progress_ label
  * issues to be tackled at some point
* Ready
  * issues with the _ready_ label
  * issues that are ready to be worked on, i.e. they are prioritized and assigned
* In Progress
  * issues with the _in progress_ label
  * this status is intended to show collaborators that someone is currently working on a particular issue
* Done
  * closed issue
  * the Done category shows issues that have been closed within the last week

# Repository strategy

## Idea

The idea is top to separate each component into its own GitHub repository. By doing it this way, GitHub actions can be used to compile the different components into .jar files.

## Repositories

- Core
- Common
- Player
- Collision
- Common Map
- Map
- Common Enemy
- Enemy
- Common Item
- HealthPotion
- Common Weapon
- Sword

## Repository setup

### Repository creation

First create a simple branch following GitHub instructions.

- Give it a name (component name).
- Give it a description
- Select Public
- Mark `Add a README file`
- Add .gitignore template `Maven`
- License not necessary

### Branching

The branching of the repository will follow the following naming scheme:

```txt
Not yet specified
```

For the default branches, a `main` and `develop` branch will contain the latest stable build and the latest development build respectively.

### Branch protection

The `main` branch has been set up with the following rules:

- `Require a pull request before merging` and `Require approvals` set to `1`
  - This disallows commits, and forces that a pull request be used to merge code into the target branch (here the `main` branch). It then requires 1 other person to approve the merge request, before it can be merged.
- `Require status checks to pass before merging`
  - This requires any GitHub actions or other types of "status checks" to pass before the merge request can be merged.
- `Require conversation resolution before merging`
  - This requires that any *conversation* (questions to code or similar in the merge request) has to be marked as *resolved*
- `Require deployments to succeed before merging`
  - If any deployments are connected to the repository, this setting prevents merge before the deployment has succeeded.
- `Do not allow bypassing the above settings`
  - This disallows administrators to just bypass the settings set up for the branch

### Default GitHub action

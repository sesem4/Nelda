# Branching

## Naming

The branching of the repository will follow the following naming scheme:

`<type>/<issue-nr>/<human readable name>`

Example:  
`feature/21/main-spi-implementation`

For the default branches, a `main` and `develop` branch will contain the latest stable build and the latest development build respectively.

BNF:
```
<branch_name> ::= <issue_type>/<issue_id>/<human_readable_name>

<issue_type> ::= "bugfix"|"feature"|"hotfix"

<issue_id> ::= <positive_integer>

<human_readable_name> ::= <string>
```

## Branching idea

Our idea for running branches, has been inspired by the Wordpress plugin [wp-htxlan](https://github.com/HTX-LAN/wp-htxlan), which has been created by one of the group members.  
Idea image:

![](https://camo.githubusercontent.com/013c364e018789203d31f87e5b5579c5a9e5653d94e240fc1cd6ade32dcf9f02/68747470733a2f2f6d656469612e646973636f72646170702e6e65742f6174746163686d656e74732f3632363439363238323738393238313831322f3734383935383635343638313537393633312f4769742d576f726b666c6f772e706e67)
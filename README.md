# Run

```
$ mvn wildfly-swarm:run
```

Debug :

```
$ mvn wildfly-swarm:run -Dswarm.debug.port=8000
```

# Query

## Format des requêtes

* méthode : POST
* content-type : JSON
* body "minimal" :

```
{ "query": "{ user(id: \"1\"){ id firstname } }" }
```

* body détaillé :  

```
{
	"operationName": "test",
	"query": "{ users { edges { node { id firstname lastname projects { edges { node { id code label } } } } } } }",
	"variables": {}
}
```

## Obtenir les informations sur le modèle

```
{
 "query":"  query IntrospectionQuery {
  __schema {
    queryType {
      name
    }
    mutationType {
      name
    }
    subscriptionType {
      name
    }
    types {
      ...FullType
    }
    directives {
      name
      description
      args {
        ...InputValue
      }
      onOperation
      onFragment
      onField
    }
  }
}

fragment FullType on __Type {
  kind
  name
  description
  fields(includeDeprecated: true) {
    name
    description
    args {
      ...InputValue
    }
    type {
      ...TypeRef
    }
    isDeprecated
    deprecationReason
  }
  inputFields {
    ...InputValue
  }
  interfaces {
    ...TypeRef
  }
  enumValues(includeDeprecated: true) {
    name
    description
    isDeprecated
    deprecationReason
  }
  possibleTypes {
    ...TypeRef
  }
}

fragment InputValue on __InputValue {
  name
  description
  type {
    ...TypeRef
  }
  defaultValue
}

fragment TypeRef on __Type {
  kind
  name
  ofType {
    kind
    name
    ofType {
      kind
      name
      ofType {
        kind
        name
      }
    }
  }
}
}"
```

## Exemples

### Utilisateurs
```
{
	"query": "{ users { edges { node { id firstname lastname } } } }"
}
```

### Utilisateur
```
{
	"query": "{ user(id:\"xxx\") { id firstname lastname } }"
}
```

### Projets
```
{
	"query": "{ projects { edges { node { id code label } } } }"
}
```

### Projet
```
{
	"query": "{ project(id:\"xxx\") { id code label } }"
}
```

### Utilisateurs et leur projets
```
{
	"operationName": "test",
	"query": "{ users { edges { node { id firstname lastname projects { edges { node { id code label } } } } } } }",
	"variables": {}
}
```

### Créer un utilisateur
```
{
	"query": "{ createUser(user: { firstname: \"John\", lastname: \"Bob\" }) { id } }"
}
```

### Affecter un projet à un utilisateur

```
{
	"query": "{ assign(user: \"xxx\", project: \"xxx\") { id firstname lastname projects { edges { node { id code label } } } } }"
}
```



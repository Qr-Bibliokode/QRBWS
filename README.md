# QRBWS  [![Build Status](https://codeship.com/projects/236e3190-14a3-0133-bdae-063b18755257/status?branch=master
)](https://codeship.com/projects/236e3190-14a3-0133-bdae-063b18755257/status?branch=master)

## REST EXAMPLE

```BASH
 curl -i -H "Accept: application/json" -X POST -d "name=authorName" localhost:8080/author/save/
 curl -i -H "Accept: application/json" http://localhost:8080/author/show/{id}
 curl -i -H "Accept: application/json" http://localhost:8080/author/index
 curl -i -H "Accept: application/json" -X PUT -d "name=Ferran" localhost:8080/author/update/{id}
-curl -i -H "Accept: application/json" -X DELETE localhost:8080/author/delete/{id}+curl -i -H "Accept: application/json" -X DELETE localhost:8080/author/delete/{id}
```


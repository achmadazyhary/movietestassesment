# Movie API RESTFull Specifications

## List of Movie
- Endpoint : /api/movies
- Method : GET
- Description : return the details of a movies in JSON format

    example :

    ```json
    {
        {
            "id" : 1,
            "title" : "Pengabdi Setan 2 Comunion",
            "description" : "dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
            "rating" : 7.0,
            "image" : "",
            "created_at" : "2022-08-01 10:56:31",
            "updated_at": "2022-08-13 09:30:23"
        },
        {
            "id" : 2,
            "title" : "Pengabdi Setan ",
            "description" : "",
            "rating" : 8.0,
            "image" : "",
            "created_at" : "2022-08-01 10:56:31",
            "updated_at": "2022-08-13 09:30:23"
        }
    }
    ```
## Detail of Movie
- Endpoint : /api/movies/:id
- Method : GET
- Description : return the details of a movies in JSON format

    example :
    
    ```json
    {
        {
            "id" : 1,
            "title" : "Pengabdi Setan 2 Comunion",
            "description" : "dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
            "rating" : 7.0,
            "image" : "",
            "created_at" : "2022-08-01 10:56:31",
            "updated_at": "2022-08-13 09:30:23"
        }   
    }
    ```

## Add New Movie
- Endpoint : /api/movies
- Method : POST
- Description : Add a Movie to the Movies list, the data will be sent as a JSON in the request body :

    example :

    ```json
    {
        "id" : 1,
        "title" : "Pengabdi Setan 2 Comunion",
        "description" : "dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
        "rating" : 7.0,
        "image" : "",
        "created_at" : "2022-08-01 10:56:31",
        "updated_at": "2022-08-13 09:30:23"
    }
    ```

## Update Movie
- Endpoint : /api/movies/:id
- Method : PATCH
- Description : Add a Movie to the Movies list, the data will be sent as a JSON in the request body :

    example :

    ```json
    {
        "id" : 1,
        "title" : "Pengabdi Setan 2 Comunion",
        "description" : "dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
        "rating" : 7.0,
        "image" : "",
        "created_at" : "2022-08-01 10:56:31",
        "updated_at": "2022-08-13 09:30:23"
    }
    ```

## Delete Movie
- Endpoint : /api/movies/:id
- Method : DELETE
- Description : Delete Movie from Database

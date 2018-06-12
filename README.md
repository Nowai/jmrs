# jmrs

<p>
    <a href="https://github.com/Nowai/muimp/blob/master/LICENSE">
       <img src="https://img.shields.io/badge/license-MIT-blue.svg"> 
    </a>
    <a href="https://github.com/Nowai/RiekiMusic/tree/master/build">
        <img src="https://img.shields.io/badge/build-passing-green.svg"> 
    </a>
    <a href="">
        <img src="https://img.shields.io/badge/version-1.2-lightgrey.svg"> 
    </a>
</p>

## Java Music REST Service

JMRS is a small Music REST service written in Java 8 utilizing Spring Boot. It allows you to access you local music files through a REST interface either locally or via the web. Specify some directories and jmrs will scan it for music files, parse meta data, create a database and launch a web server providing a REST interface to query your music.   

## Building

Requires git, maven >3.5v and Java >8 sdks.

```git clone https://github.com/Nowai/jmrs```

clone the repository

```mvn install```

install dependencies

```mvn compile```

compile source

## API

You can access your music through the following commands. Currently, it is not possible to add songs remotely - jmrs is meant to provide a simple method to access your local files only.

### Song

Returns information about a specific song

#### URL

/songs/[songId:integer]

#### Method

`GET`

#### URL params

none

#### Response

**success-code**: `200`

```
	{
		"songId": integer,
		"title": string,
		"artist": string,
		"artistId": integer,
		"album": string,
		"albumId": integer,
		"duration": integer, // time in seconds
		"rating": integer, // 0-5 star rating
		"cover_url": string // url to cover
	}
```
### Song File

Returns the music file in binary format via content-disposition: attachment

#### URL

/songs/file/[songId:integer]

#### URL params

none

#### Response

**success-code**: `200`

content-disposition -> attachment;

### Song Cover

Returns the cover of a specific music file. See `cover_url` for specific URL, the default way to access the cover is listed here and should work for all songs.

#### URL

/songs/cover/[songId:integer]

#### Method

`GET`

#### URL params

none

#### Response

**success-code**: `200`

content-type -> image/jpeg

### Songs

Returns a list of songs, ordered alphabetically(asc) by song title and paged (default page size: 50).

#### URL

/songs/

#### Method

`GET`

#### URL params

**optionally**
`page=[integer]`

Providing no page parameter is equal to `page=0`.

#### Response

**success-code**: `200`

```
{
	"next_href": "/songs?page=X", // url to the next page
	"songs": [
		{
			"songId": integer,
			"title": string,
			"artist": string,
			"artistId": integer,
			"album": string,
			"albumId": integer,
			"duration": integer, // time in seconds
			"rating": integer, // 0-5 star rating
			"cover_url": string // url to cover
		}
	]
}
```

### Artist

Returns information about a specific artist

#### URL

/artist/[artistId:integer]

#### Method

`GET`

#### URL params

none

#### Response

**success-code**: `200`

```
{
	"artistId": integer,
	"artistName": string,
	"albums": [
		{
			"albumId": integer,
			"albumTitle": string,
			"artistId": integer,
			"artistName": string,
			"songs": [
				{
					"songId": integer,
					"title": string,
					"artist": string,
					"artistId": integer,
					"album": string,
					"albumId": integer,
					"duration": integer, // time in seconds
					"rating": integer, // 0-5 star rating
					"cover_url": string // url to cover
				}
			]
		}
	]
}
```

### Artists

Returns a list of artists, ordered alphabetically(asc) by artist name and paged (default page size: 50).

#### URL

/artists/

#### Method

`GET`

#### URL params

**optionally**
`page=[integer]`

Providing no page parameter is equal to `page=0`.

#### Response

**success-code**: `200`

```
{
	"next_href": "/artists?page=X", // url to the next page
	"artists": [
		{
			"artist": string,
			"artistId": integer,
			"songCount": integer, // number of songs this artist has
			"albumCount": integer, // number of albums this artist has
			"cover_url": string // a cover for the artist
		}
	]
}
```

### Album

Returns information about a specific album

#### URL

/albums/[albumId:integer]

#### Method

`GET`

#### URL params

none

#### Response

**success-code**: `200`

```
{
	"albumId": integer,
	"albumTitle": string,
	"artistId": integer,
	"artistName": string,
	"songs: [
		{
			"songId": integer,
			"title": string,
			"artist": string,
			"artistId": integer,
			"album": string,
			"albumId": integer,
			"duration": integer, // time in seconds
			"rating": integer, // 0-5 star rating
			"cover_url": string // url to cover
		}
	]
}
```

### Albums

Returns a list of albums, ordered alphabetically(asc) by album title and paged (default page size: 50).

#### URL

/albums/

#### Method

`GET`

#### URL params

**optionally**
`page=[integer]`

Providing no page parameter is equal to `page=0`.

#### Response

**success-code**: `200`

```
{
	"next_href": "/albums?page=X", // url to the next page
	"albums": [
		{
			"albumTitle": string,
			"albumId": integer,
			"artistName": string,
			"artistId": integer,
			"songCount": integer, // number of songs in this album
			"cover_url": string // cover for the album
		}
	]
}
```

### Favorites

TBD



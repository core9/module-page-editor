A/B rule flow


    1. hostname
    2. page
    3. request
    4. useragent
    5. geo
    6. period
    7. percentage



    1. request key

    http://easydrain.localhost/p/scraper/nl

    2. get active tests based on period



    3. { request=xmas : ua=ipad : geo=nl }

    if useragent active in test get useragent tests

    if request active in test get request tests

    if geo active in tests get geo tests



    4. if multiple tests get percentage rules

    pick a test version




A/B rule format


    1. registry with url as key

    {
      key : "http://easydrain.localhost/p/scraper/nl",
      tests : {[
        {
          request:xmas,
          ua:ipad,
          geo:nl
        },{
          request:xmas,
          ua:ipad,
          geo:nl
        }
      ]}
    }




















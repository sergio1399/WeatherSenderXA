CREATE TABLE IF NOT EXISTS City
(
  id INTEGER PRIMARY KEY,
  name VARCHAR(64),
  region VARCHAR(64),
  country VARCHAR(64)
);

CREATE SEQUENCE IF NOT EXISTS forecast_ids;
CREATE TABLE IF NOT EXISTS Forecast
(
  id INTEGER PRIMARY KEY DEFAULT NEXTVAL('forecast_ids'),
  city_id INTEGER REFERENCES City(id),
  temperature VARCHAR(32),
  wind VARCHAR(128),
  text VARCHAR(64),
  pressure REAL,
  visibility REAL,
  forecast_date DATE
);

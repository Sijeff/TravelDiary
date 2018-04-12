/****** Script for SelectTopNRows command from SSMS  ******/
SELECT TOP (1000) [userID]
      ,[name]
      ,[birthday]
      ,[email]
      ,[password]
      ,[registrationDate]
  FROM [Academy_Projekt5].[dbo].[users]

INSERT INTO journeys(title, user_ID)
VALUES ('wonderful trip to norwat', 1);

INSERT INTO journeys(title, user_ID)
VALUES ('great great great!', 1);

INSERT INTO journeys(title, user_ID)
VALUES ('another test', 1);

INSERT INTO journeys(title, user_ID)
VALUES ('this great trip!', 1);

SELECT TOP (1000) [journeyID]
      ,[title]
      ,[user_ID]
  FROM [Academy_Projekt5].[dbo].[journeys]

INSERT INTO journeyParts(startDate, endDate, journey_ID, title, text)
VALUES ('2008-01-01', '2012-12-12', 1, 'title1', 'text describing this thing');

INSERT INTO journeyParts(startDate, endDate, journey_ID, title, text)
VALUES ('2008-01-01', '2012-12-12', 1, 'title2', 'text describing another thing');

INSERT INTO journeyParts(startDate, endDate, journey_ID, title, text)
VALUES ('2008-01-01', '2012-12-12', 2, 'title again', 'text describing yet another thing');

INSERT INTO journeyParts(startDate, endDate, journey_ID, title, text)
VALUES ('2008-01-01', '2012-12-12', 3, 'title once again', 'another text describing yet another thing');

SELECT TOP (1000) [journeyPartID],[startDate],[endDate],[journey_ID],[title],[text]
  FROM [Academy_Projekt5].[dbo].[journeyParts]

SELECT *
FROM users
USE Academy_Projekt5;

DROP TABLE locations, journeyParts,journeys,users

CREATE TABLE users (
	userID int IDENTITY(1,1),
	name nvarchar(80) not null,
	birthday date not null,
	email nvarchar(80) not null,
	password nvarchar(80) not null,
	registrationDate date not null,
	CONSTRAINT emailValidFormat  CHECK(email LIKE '%___@___%.__%'),
	CONSTRAINT PK_userID PRIMARY KEY (userID),
	CONSTRAINT CHK_birthday CHECK (birthday <= GETDATE())
);

CREATE TABLE journeys (
	journeyID int IDENTITY(1,1),
	title nvarchar(80) not null,
	user_ID int not null,
	CONSTRAINT FK_UsersJourney FOREIGN KEY (user_ID)
    REFERENCES users(userID),
	CONSTRAINT PK_journeyID PRIMARY KEY (journeyID)

);

CREATE TABLE locations (
	locationID int IDENTITY(1,1),
	placeName nvarchar(80) not null,
	country nvarchar(80) not null,
	lat float not null,
	lng float not null,
	CONSTRAINT PK_locationID PRIMARY KEY (locationID)
);

CREATE TABLE journeyParts (
	journeyPartID int IDENTITY(1,1),
	location_ID int not null,
	journey_ID int not null,
	startDate date not null,
	endDate date not null,
	title nvarchar(80),
	text nvarchar(2000) not null,
	CONSTRAINT FK_JourneysJourneyPart FOREIGN KEY (journey_ID)
    REFERENCES journeys(journeyID),
	CONSTRAINT FK_JourneyPartLocation FOREIGN KEY (location_ID)
	REFERENCES locations(locationID),
	CONSTRAINT PK_journeyPartID PRIMARY KEY (journeyPartID)
);




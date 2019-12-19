package digitized.gamehub.utilities

import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameParty
import digitized.gamehub.domain.GameType
import digitized.gamehub.domain.Location
import digitized.gamehub.domain.User
import digitized.gamehub.domain.asDatabaseModel
import java.text.SimpleDateFormat

var game1 = Game(
    id = "5dd021b602611d001e968391",
    name = "Mario Party",
    description = "You pick a character and try to get as many stars as you can. You can buy stars with coins that you earn along the way or by winning the round challenges.",
    rules = "Each rond you throw a dice and move the amount of squares you threw. At the end of each round, there is a minigame that can either win or lose you coins. Buy stars with Coins. At the end of 10 rounds, the one with the most stars wins.",
    requirements = "Wii U console.",
    type = GameType.PARTY_GAME,
    visible = true
)

var gameEntity1 = game1.asDatabaseModel()

var game2 = Game(
    id = "5db76b7430957f0ef05e73fa",
    name = "Game of Hearts",
    description = "A card game where you aim to get as least points as you can.",
    rules = "All heart cards are worth 1 point, the queen of spade is 13. You need to get the least amount of point or you go all out and aim for 26 points. When this happens, all the others get 26 points. Th first one with 100+ points loses. The two of clubs begins.",
    requirements = "4 players,1 deck of cards",
    type = GameType.CARD_GAME,
    visible = true
)

var gameEntity2 = game2.asDatabaseModel()

var game3 = Game(
    id = "5dd0200902611d001e96838e",
    name = "League of Legends",
    description = "LoL is a MOBA type game where you play as a team of 5 players versus 5 other players. The first one to destroy the others homebase (Nexus) wins.",
    rules = "Destory turrets and inhibitors to eventually destory the Nexus and try to win.",
    requirements = "You need to have a LoL account",
    type = GameType.VIDEO_GAME,
    visible = true
)

var gameEntity3 = game3.asDatabaseModel()

var party1 = GameParty(
    id = "5dd0260102611d001e968395",
    name = "Master LoL Scrimms",
    date = SimpleDateFormat("yyyy-MM-dd").parse("2020-2-29")!!,
    maxSize = 5,
    participants = arrayOf("5db8838eaffe445c66076a88"),
    declines = arrayOf(""),
    gameId = "5dd0200902611d001e96838e",
    location = Location(
        type = "Point",
        coordinates = doubleArrayOf(51.0416, 3.71426)
    )
)

var partyEntity1 = party1.asDatabaseModel()

var party2 = GameParty(
    id = "5dd0272b02611d001e968398",
    name = "Mario 'n Chill",
    date = SimpleDateFormat("yyyy-MM-dd").parse("2020-1-25")!!,
    maxSize = 4,
    participants = arrayOf("5db8838eaffe445c66076a88"),
    declines = arrayOf(""),
    gameId = "5dd021b602611d001e968391",
    location = Location(
        type = "Point",
        coordinates = doubleArrayOf(51.0572, 3.57238)
    )
)

var partyEntity2 = party2.asDatabaseModel()

var party3 = GameParty(
    id = "5dd0272b02611d001e968399",
    name = "Mario 'n Chill2",
    date = SimpleDateFormat("yyyy-MM-dd").parse("2020-1-25")!!,
    maxSize = 4,
    participants = arrayOf("5db8838eaffe445c66076a88"),
    declines = arrayOf(""),
    gameId = "5dd021b602611d001e968391",
    location = Location(
        type = "Point",
        coordinates = doubleArrayOf(51.0572, 3.57238)
    )
)

var partyEntity3 = party3.asDatabaseModel()

var user1 = User(
    id = "5db8838eaffe445c66076a88",
    firstName = "test",
    lastName = "user",
    email = "testUser@email.com",
    maxDistance = 10,
    latitude = 51.0538286,
    longitude = 3.7250121
)

var userEntity1 = user1.asDatabaseModel()

var user2 = User(
    id = "5db8838eaffe445c66076a99",
    firstName = "test",
    lastName = "User2",
    email = "testUser2@email.com",
    maxDistance = 25,
    latitude = 51.0538286,
    longitude = 3.7250121
)

var userEntity2 = user2.asDatabaseModel()

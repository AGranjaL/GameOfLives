# GameOfLives
Android Card game with IA
##General Features:

Individual game

2 players

Spanish deck (40 cards)

###Summary: 

The game of lives consists in betting on the number of tricks you will win at the beginning of each round. For this purpose you will know your cards, but not of the rest of the players. Therefore, you should base your decision on your cards, as well as on the bids of your opponents.

At the beginning of the game, each player will have 5 lives. If you lose all your lives, the game will be over for you.

The player that remains, once the rest are eliminated, wins.

###Objective:

The objective of the players will be to bet at the beginning of each round the numbers of tricks that they will win, the difference between your bid and the actual number of tricks that you actually win in the game will be the amount of lives you will lose.

###Game:

The game starts when the “hand” player deals 5 cards to each player. Players will then bet on how many tricks they will win in that round. The first player to bid will be the player on the left of the “hand” player, and they will continue from them clockwise, so that the last player to bid will be the “hand” player. In this same order, they will make their move.

Once each round is over, it’s shuffled again and the process is repeated, dealing one less card to each player in each of the following rounds, until reaching 1 card per player, at which point 5 cards will be dealt again. 

###Value of each card:

Cards are arranged according to their number and regardless of suit. The king is the highest, and the ace, the lowest.

The trick winner will be the one who throws the card with the highest value. In case two or more player tie, the trick will be for the one who throws first in the round.

###Lives:

Initially, each of the players will have five lives. Every round, players will lose as many lives as the difference between their bid and the total number of tricks won, until they lose all their lives.

There are no kind of reward with lives. Players won’t add up lives in any of the rounds neither by winning the bet nor by staying alive for a number of rounds.

###Key issues:
####Trick: group of cards thrown by the players in one cycle (one card from each player)

####“Hand” player: Before each round, one of the players shuffles the deck and deals the cards. This player is called “hand” player, and will be the last player bidding. In each round, this role rotates clockwise among all the players still alive. “Hand” player will be chosen randomly between all the players.

####Number of lives bet: In every round, the amount of all the bids cannot be the same as the number of tricks played (number of cards each player has at the beginning). “Hand” player has the privilege of knowing all the bids, but with the handicap of having to adapt his bid to this rule.

####Number of cards: The number of cards dealt by “hand” player is not always the same. In the first round, five cards will be dealt, and each round one will be subtracted until reaching the round with one card per player. After this round, the cycle will be restarted, dealing five cards again (until only one of all the players continues alive).

####Mirror round: The round with one card for each player is called mirror round. In this round, contrary to what happens in the others, every player has to bid knowing their opponents cards, but without knowing theirs.

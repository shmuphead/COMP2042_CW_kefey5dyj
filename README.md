# Brick_Destroy
This is a simple arcace video game.
Player's goal is to destroy a wall with a small ball.
The game has  very simple commmand:
SPACE start/pause the game
A move left the player
D move rigth the player
ESC enter/exit pause menu
ALT+SHITF+F1 open console
the game automatically pause if the frame loses focus

Enjoy ;-)

Refactor changes:
- Change Player().movRight() to moveRight()
- Adding getter functions for Ball and Wall class
- Moving Wall class to wall package

- Improve functions' argument readability
- Replacing direct access into object's attribute with getter functions
- More package restructuring

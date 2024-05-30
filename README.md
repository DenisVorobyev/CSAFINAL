used this guide for the early development, up to using matrices for transformation of graphics:https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/
GameObject was created by me, along with the framework of renderscene.
Camera was created by me, i understand that getting the world matrix with view matrix already added is kind of silly but it works!
https://www.r-5.org/files/books/computers/algo-list/game-development/Gino_van_den_Bergen-Collision_Detection_in_Interactive_3D_Environments-EN.pdf
This book was used to impliment collision detection
Basic framework:
Object moves -> check for collisions in nearby area -> if collided -> use collision data to perform collision/keep objects out of each other(data like dt, speed, collision position, etc)
due to time constraints i have decided to restrict myself to cubes :(
ah it turns out my camera code is broken and i just noticed! time to fix!
in the end, theres no game. just a small little world to run around. how silly.

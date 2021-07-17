# Projection & Collision Simulation &middot; [![GitHub](https://img.shields.io/github/license/ali-mohamed-nasser/Projection-and-Collision)](https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/LICENSE)

### Introduction
In this project, we dealt with the physical study of the idea of throwing solid objects under different conditions, factors, and environments, and we followed this up with a physical study to know the reactions resulting from this ejection, such as these objects hitting surfaces, rebounding from them and colliding with other solid objects, or studying the friction factor resulting from these objects touching surfaces and this factor. It varies according to the material of the object or the surface studied
Then we converted this “theoretical” study into a computer simulation, with the aim of achieving a study close to reality for this physical idea and we followed that up by adding other features. This simulation has perfect flexibility, such as the possibility of changing each parameter during the run time. After that, we added some other improvements to ensure better performance. Thus, an ideal user experience, such as adding light and sound effects to objects projectile. 
And here we have provided a detailed explanation of this study, which includes two main parts as following:
-	Theoretical physical study.
-	Computer simulations for this study.

# Theoretical physics study in the ideal case
Firstly, we make a physical study of the motion of the projectile under ideal conditions, without air resistance and winds. Where we noticed that we need the coordinates of the projected object in three-dimensional space for each moment ``` time (x, y, z) ``` in order to apply the necessary simulation.

### The forces acting on the projectile ball
Assuming that the projected object is a ball, the forces acting on this ball under ideal conditions are the gravitational force, which is the force that pulls the projectile downwards, meaning that its direction is always towards the ground. And by applying the basic law of motion, we find:
<br/><br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_1.png" width="640" ><br/><br/>
The acceleration of a projectile ball is the same as the acceleration of gravity under ideal conditions.

### Projected angle
After studying the forces acting on the projectile, we need to study the angle of the projectile, but given that, we do this study in a three-dimensional space instead of the normal study in the cartesian plane in this case, we need two angles instead of one.
<br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_2.png">

### Projectile velocity
One of the basic data that we will need at the beginning of the study is the ``` initial projectile velocity ``` which we will proceed from it to study the velocity and coordinates of the projectile at every moment. To achieve this, we need to find the velocity components on the three coordinate axes by projecting the velocity ray primary on these axes.
<p align="center"><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_3.png" width="750"></p>

### Projectile Coordinates
Based on the previous data, the coordinates of the projectile at each moment are as follows:
<br/><br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_5.png" width="350"><br/><br/>
Thus, we have studied the motion of the projectile in ideal conditions for each moment, but this study is far away from reality, as there are many other factors that can affect the trajectory and velocity of this projectile and this is what we touched on in the second stage of the physical study.

# Continuing the physical study with external factors
### Air resistance
As we mentioned earlier, the projected object in the real world is affected by several external factors, these factors are air and wind resistance, and it belongs to a more general term called resistance of fluid: a term used in fluid dynamics to refer to the forces that impede the passage of the body through a fluid substance (liquid or gas), and in our case, the air is one of the gaseous fluids.
The force of air resistance is given by:
<br/><br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_6.png"></br></br>
K: is the air resistance drag parameter, and it is a fixed value that varies according to the environment in which the ejaculation takes place, as it is affected by weather factors like different types of air, such as air humidity and density, where its value on earth in the ranges of 1.0 - 1.
And since we have a new force that affects the projectile, we must calculate its effect on its speed and its path at every moment, based on the basic law of movement like the following:
<br/><br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_8.png"><br/>

And based on the formula **( * )** we have projected on the coordinate axes in the three-dimensional plane where we deduced the following laws for the added value of speed vehicles at every moment, and they are as follows:
<br/><br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_10.png" width="580"><br/><br/>
Thus, we have finished the theoretical study of the trajectory of the projectile's movement with the various weather factors.

# The physical study of body collision and rebound
After studying the motion path of the projected object, we extended this study to include the physics of collision objects and colliding with different factors, for example, when a golden object collides with the ground, then its velocity after impact differs from the velocity resulting from a rubber collision with the ground itself, and this is one of the factors we covered in our study.

### Rebound angle
When we talk about the concept of impact and rebound, the angle of reverberation for this rebounding body will be the first thing that must be studied; It is a term indicating the new angle at which the body will move. However, this angle differs from the method of calculating it according to the surface on which the object hits. We will deal with two main parts to study this angle. The first part is the impact of the body on a flat surface” like the earth. In addition to the second part in which we dealt with the study of the impact of the body on other bodies.

#### Rebound angle with flat objects
The rebound angle in this case is always equal to the angle of incidence ``` the collision angle ```, which is the angle formed by the projectile trajectory ray with the surface at the moment of impact.
<p align="center"><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_11.png" width="490"></p>

#### The concept of bouncing rays ( normal vectors )
Every object in real life or in a computer simulation is actually made up of a number of points. To a spherical body, it is in fact not a single body, but rather a large number of points or atoms contiguous, which form the spherical shape of this body, and based on these points that make up this body, every point has a hypothetical ray outside of it, and this ray is called the point ray on the vertical vector, and that vector always reflects the direction of this point in space. And this idea is illustrated in the following figure:
<p align="center"><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_12.png" width="220"></p>

#### Rebound angle with other objects
The rebound angle in this case is in the direction of the vertical ray of the point of collision of the first body by the other, for example, in the event of two balls colliding, the rebound angle for them will be as in this figure:
<p align="center"><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_13.png" width="500"></p>

But this case is true only in the case of static collision and we will have found the angle only for a stationary body, but what if we want to calculate the angle of reversion for a moving body or if both bodies are moving?
So in this case, we have to collect rays, which is a term that refers to the combination of two or more rays to produce the value and direction of the new ray and in this case we have to get the incoming ray of the object moving with the vertical ray of the impact point in the other body and thus the direction of the final rays will be as shown in the following shape:

<p align="center"><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_14.png" width="360"></p>
And by that, we have found the right ray in all different cases, and we will notice that the first case is actually a special case of the second, and more general case, since the ray of the rebound angle is always directed at the earth upwards, so by summing it up with the income ray, we get the rebound ray that is the same as the income ray but in the opposite direction.

### Calculate the velocity of the body after the impact
Based on the law of energy conservation, which states that energy is neither destroyed nor created from nothing, but it moves from one form to another but first, we will consider that the energy will take the kinetic form. Thus, the amount of motion before the impact it will be equal to the amount of movement after the impact.
<br/><br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_15.png" width="330"><br/>

We notice from the previous relationship that we need the value of v`1, v`2. It is the velocity of the first body and the second body after the impact and in order to be able to find these two values we will need two equations with two unknown parameters, so we will project the velocity ray on two coordinate axes either yz, xz or xy.
We will choose the xy axes, so it will be:
<br/><br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_16.png" width="320"><br/><br/>
And starting from the formula (1):
<br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_17.png" width="420"><br/>
And we know that:
<br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_18.png" width="300"><br/>
So finally we get:
<br/><img src="https://github.com/ali-mohamed-nasser/Projection-and-Collision/blob/main/images/image_19.png" width="675">

#### And the angles are:
- **ϴ1**: Ejection angle on the vertical plane before impact.
- **θ2**: The angle of the ejection on the horizontal plane before impact.
- **θ3**: The angle of ejection on the vertical plane is the angle of vertical rebound.
- **θ4**: the angle of ejection on the horizontal plane the angle of horizontal rebound.

And now we starting from the formula (2) and with the same way:

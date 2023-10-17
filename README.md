# Barnsley Fern

![barnsley_fern](https://github.com/Movorg/barnsley-fern/assets/60103760/d9aa5084-2dbd-41da-866c-ce007c0dfac1)

Visualization of the construction of the fractal "Barnsley Fern" in Java.

# Features

*   You can change the following parameters (all these parameters can be changed directly during the rendering process):
      * point color;
      * poin size;
      * drawing speed of elements.
*   You can stop and resume the rendering process from the same place.

# What is Barnsley Fern?

The **Barnsley Fern** is a fascinating and visually striking fractal pattern that was discovered by British mathematician and computer scientist Michael Barnsley in 1988. This fractal is renowned for its intricate, fern-like appearance and is a prominent example of an iterated function system (IFS) fractal.

# Mathematical Foundations

The Barnsley Fern is generated through a series of mathematical transformations applied to a single point in the complex plane (usually referred to as the "seed" or "initial point"). These transformations are defined by a set of four affine transformations, each with an associated probability:

1. **Stem Transformation:** 
   - Probability: 1%
   - Transformation:
     - x' = 0
     - y' = 0.16y

2. **Successively Smaller Leaflet Transformation:**
   - Probability: 85%
   - Transformation:
     - x' = 0.85x + 0.04y
     - y' = -0.04x + 0.85y + 1.6

3. **Larger Left Leaflet Transformation:**
   - Probability: 7%
   - Transformation:
     - x' = 0.2x - 0.26y
     - y' = 0.23x + 0.22y + 1.6

4. **Larger Right Leaflet Transformation:**
   - Probability: 7%
   - Transformation:
     - x' = -0.15x + 0.28y
     - y' = 0.26x + 0.24y + 0.44

These transformations are applied iteratively to the initial point, with the selection of each transformation determined by its associated probability. As the iterations progress, they give rise to the intricate self-replicating patterns that characterize the Barnsley Fern.

# import the necessary packages
from collections import OrderedDict
import numpy as np
import cv2
import argparse
import dlib
import imutils
import sys



facial_features_cordinates = {}


# define a dictionary that maps the indexes of the facial
# landmarks to specific face regions
FACIAL_LANDMARKS_INDEXES = OrderedDict([
    ("left_ball", (0, 9)),
    ("right_ball", (9, 17)),
    ("nose", (17, 21)),
])
                                                                                                                                                                                                    



def shape_to_numpy_array(shape, dtype="int"):
    

    # initialize the list of (x, y)-coordinates
    coordinates = np.zeros((21, 2), dtype=dtype)

    # loop over the 68 facial landmarks and convert them
    # to a 2-tuple of (x, y)-coordinates
    
    coordinates[0] = (shape.part(0).x, shape.part(0).y)
    coordinates[1] = (shape.part(1).x, shape.part(1).y)
    coordinates[2] = (shape.part(2).x, shape.part(2).y)
    coordinates[3] = (shape.part(3).x, shape.part(3).y)
    coordinates[4] = (shape.part(4).x, shape.part(4).y)
    coordinates[5] = (shape.part(5).x, shape.part(5).y)
    coordinates[6] = (shape.part(31).x, shape.part(31).y)
    coordinates[7] = (shape.part(40).x, shape.part(40).y)
    coordinates[8] = (shape.part(36).x, shape.part(36).y)

    coordinates[9] = (shape.part(11).x, shape.part(11).y)
    coordinates[10] = (shape.part(12).x, shape.part(12).y)
    coordinates[11] = (shape.part(13).x, shape.part(13).y)
    coordinates[12] = (shape.part(14).x, shape.part(14).y)
    coordinates[13] = (shape.part(15).x, shape.part(15).y)
    coordinates[14] = (shape.part(27).x, shape.part(27).y)
    coordinates[15] = (shape.part(35).x, shape.part(35).y)
    coordinates[16] = (shape.part(54).x, shape.part(54).y)

    coordinates[17] = (shape.part(27).x, shape.part(27).y)
    coordinates[18] = (shape.part(31).x, shape.part(31).y)
    coordinates[19] = (shape.part(33).x, shape.part(33).y)
    coordinates[20] = (shape.part(36).x, shape.part(36).y)
    

    # return the list of (x, y)-coordinates
    return coordinates


def visualize_facial_landmarks(image, shape, colors=None, alpha=0.75):

    

    # create two copies of the input image -- one for the
    # overlay and one for the final output image
    overlay = image.copy()
    output = image.copy()

    

    # if the colors list is None, initialize it with a unique

    # color for each facial landmark region
    if colors is None:
        colors = [(19, 199, 109), (79, 76, 240), (230, 159, 23),
                  (168, 100, 168), (158, 163, 32),
                  (163, 38, 32), (180, 42, 220)]

    

    # loop over the facial landmark regions individually
    for (i, name) in enumerate(FACIAL_LANDMARKS_INDEXES.keys()):
        

        # grab the (x, y)-coordinates associated with the
        # face landmark
        (j, k) = FACIAL_LANDMARKS_INDEXES[name]

        

        pts = shape[j:k]
        facial_features_cordinates[name] = pts

        # check if are supposed to draw the jawline
        if name == "Jaw":
            

            # since the jawline is a non-enclosed facial region,
            # just draw lines between the (x, y)-coordinates
            for l in range(1, len(pts)):
                ptA = tuple(pts[l - 1])
                ptB = tuple(pts[l])
                cv2.line(overlay, ptA, ptB, colors[i], 2)

        # otherwise, compute the convex hull of the facial
        # landmark coordinates points and display it
        else:
            hull = cv2.convexHull(pts)
            cv2.drawContours(overlay, [hull], -1, colors[i], -1)

    # apply the transparent overlay
    cv2.addWeighted(overlay, alpha, output, 1 - alpha, 0, output)

    # return the output image
    #print(facial_features_cordinates)
    return output



# initialize dlib's face detector (HOG-based) and then create
# the facial landmark predictor
detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor('shape_predictor_68_face_landmarks.dat')



a= sys.argv
# load the input image, resize it, and convert it to grayscale
image = cv2.imread('./uploads/%s'%a[1])

image = imutils.resize(image, width=500)
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)



# detect faces in the grayscale image
rects = detector(gray, 1)

# loop over the face detections
for (i, rect) in enumerate(rects):
    

    # determine the facial landmarks for the face region, then
    # convert the landmark (x, y)-coordinates to a NumPy array
    shape = predictor(gray, rect)
    shape = shape_to_numpy_array(shape)

    

    output = visualize_facial_landmarks(image, shape)
    
    a[1] = 'new'+a[1]
    cv2.imwrite('./uploads/%s'%a[1],output)
    

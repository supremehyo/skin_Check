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
    ("Mouth", (48, 68)),
    ("Right_Eyebrow", (17, 22)),
    ("Left_Eyebrow", (22, 27)),
    ("Right_Eye", (36, 42)),
    ("Left_Eye", (42, 48)),
    
    ("Jaw", (0, 17))
])
def face_remap(shape): 
    remapped_image = shape.copy() 
    # left eye brow 
    remapped_image[17] = shape[26] 
    remapped_image[18] = shape[25] 
    remapped_image[19] = shape[24] 
    remapped_image[20] = shape[23] 
    remapped_image[21] = shape[22] 
    # right eye brow 
    remapped_image[22] = shape[21] 
    remapped_image[23] = shape[20] 
    remapped_image[24] = shape[19] 
    remapped_image[25] = shape[18] 
    remapped_image[26] = shape[17] 
    # neatening 
    remapped_image[27] = shape[0] 

    return remapped_image 
def left_face(shape):
    remapped_image = shape.copy() 
    remapped_image[6]=shape[31]
    remapped_image[7]=shape[40]
    remapped_image[8]=shape[36]
    return remapped_image 

def right_face(shape):
    remapped_image = shape.copy() 
    remapped_image[0]=shape[11]
    remapped_image[1]=shape[12]
    remapped_image[2]=shape[13]
    remapped_image[3]=shape[14]
    remapped_image[4]=shape[15]
    remapped_image[5]=shape[27]
    remapped_image[6]=shape[35]
    remapped_image[7]=shape[54]
    return remapped_image 

def nose(shape):
    remapped_image = shape.copy() 
    remapped_image[0]=shape[27]
    remapped_image[1]=shape[31]
    remapped_image[2]=shape[33]
    remapped_image[3]=shape[36]
    return remapped_image
                                                                                                                                                                                                
def shape_to_numpy_array(shape, dtype="int"):
    
    # initialize the list of (x, y)-coordinates
    coordinates = np.zeros((68, 2), dtype=dtype)

    # loop over the 68 facial landmarks and convert them
    # to a 2-tuple of (x, y)-coordinates
    for i in range(0, 68):
        coordinates[i] = (shape.part(i).x, shape.part(i).y)

    # return the list of (x, y)-coordinates
    return coordinates

def visualize_facial_landmarks(image, shape, colors=None, alpha=1):

    # create two copies of the input image -- one for the
    # overlay and one for the final output image
    overlay = image.copy()
    output = image.copy()
    
    # if the colors list is None, initialize it with a unique

    # color for each facial landmark region
    if colors is None:
        colors = [(0,0,0), (0,0,0), (0,0,0),
                  (0,0,0), (0,0,0),
                  (0,0,0), (0,0,0)]

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
            #cv2.drawContours(overlay, [hull], -1, colors[i], -1)
            cv2.fillConvexPoly(overlay,hull,1,255)

    # apply the transparent overlay
    cv2.addWeighted(overlay, alpha, output, 1 - alpha, 0, output)

    # return the output image
    ######print(facial_features_cordinates)
    return output

# initialize dlib's face detector (HOG-based) and then create
# the facial landmark predictor
detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor('shape_predictor_68_face_landmarks.dat')

# load the input image, resize it, and convert it to grayscale
a = sys.argv
print(a)
image = cv2.imread('./uploads/%s'%a[1])

image = imutils.resize(image, width=400)
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

out_face1 = np.zeros_like(image)
out_face2 = np.zeros_like(image)
out_face3 = np.zeros_like(image)

# detect faces in the grayscale image
rects = detector(gray, 1)

# loop over the face detections
for (i, rect) in enumerate(rects):

    # determine the facial landmarks for the face region, then
    # convert the landmark (x, y)-coordinates to a NumPy array
    shape = predictor(gray, rect)
    shape = shape_to_numpy_array(shape)
    
    output1 = visualize_facial_landmarks(image, shape)
    output2 = visualize_facial_landmarks(image, shape)
    output3 = visualize_facial_landmarks(image, shape)

    feature_mask1 = np.zeros((output1.shape[0], output1.shape[1]))
    feature_mask2 = np.zeros((output2.shape[0], output2.shape[1]))
    feature_mask3 = np.zeros((output3.shape[0], output3.shape[1]))

    remapped_shape_nose =  nose(shape)
    remapped_shape_left =  left_face(shape)
    remapped_shape_right =  right_face(shape)
    
    
    cv2.fillConvexPoly(feature_mask1, remapped_shape_nose[0:3], (255,255,255))
    cv2.fillConvexPoly(feature_mask2, remapped_shape_left[0:8], (255,255,255))
    cv2.fillConvexPoly(feature_mask3, remapped_shape_right[0:7], (255,255,255))
    feature_mask1 = feature_mask1.astype(np.bool) 
    feature_mask2 = feature_mask2.astype(np.bool)
    feature_mask3 = feature_mask3.astype(np.bool)
    
    out_face1[feature_mask1] = output1[feature_mask1]
    out_face2[feature_mask2] = output2[feature_mask2]
    out_face3[feature_mask3] = output3[feature_mask3]
 #   rows,cols,_ = out_face1.shape
#    rows2,cols2,_2 = out_face2.shape
    rows3,cols3,_3 = out_face3.shape

#    count=0
#    for i in range(rows):
#        for j in range(cols):
#            b,g,r = out_face1[i,j]
#            if b!=0 and g!=0 and r!=0:
#                count=count+1
#    count2=0
#    for i in range(rows2):
#        for j in range(cols2):
#            b,g,r = out_face2[i,j]
#            if b!=0 and g!=0 and r!=0:
#                count2=count2+1
    count3=0
    for i in range(rows3):
        for j in range(cols3):
            b,g,r = out_face3[i,j]
            if b!=0 and g!=0 and r!=0:
                count3=count3+1


    # rows,cols,_= out_face1.shape
    # for i in range(rows):
    #     for j in range(cols):
    #         b,g,r = out_face1[i,j]
    #         if b==0 and g==0 and r==0:
    #             out_face1[i,j] = [0,0,255]
    # rows,cols,_= out_face2.shape
    # for i in range(rows):
    #     for j in range(cols):
    #         b,g,r = out_face2[i,j]
    #         if b==0 and g==0 and r==0:
    #             out_face2[i,j] = [0,0,255]
    
    #cv3.imwrite("output1-beforefilter.jpg",out_face1)
    #cv2.imwrite("output2-beforefilter.jpg",out_face2)
    #cv2.imwrite("output3-beforfilter.jpg",out_face3)
    
    out_face1fillter = cv2.bilateralFilter(out_face1,30,75,75)
    out_face2fillter = cv2.bilateralFilter(out_face2,30,75,75)
    out_face3fillter = cv2.bilateralFilter(out_face3,30,75,75)
    # rows,cols,_= out_face1fillter.shape
    # for i in range(rows):
    #     for j in range(cols):
    #         b,g,r = out_face1fillter[i,j]
    #         if b==0 and g==0 and r==255:
    #             out_face1fillter[i,j] = [0,0,254]
    # rows,cols,_= out_face2fillter.shape
    # for i in range(rows):
    #     for j in range(cols):
    #         b,g,r = out_face2fillter[i,j]
    #         if b==0 and g==0 and r==255:
    #             out_face2fillter[i,j] = [0,0,254]
    #cv2.imshow("Image", output)
    #cv2.imwrite("output1-afterfilter.jpg",out_face1fillter)
    #cv2.imwrite("output2-afterfilter.jpg",out_face2fillter)
    #cv2.imwrite("output3-afterfilter.jpg",out_face3fillter)

    out_face1sub = cv2.subtract(out_face1,out_face1fillter)
    out_face2sub = cv2.subtract(out_face2,out_face2fillter)
    out_face3sub = cv2.subtract(out_face3,out_face3fillter)
    
    out_face1Normal = cv2.normalize(out_face1sub,None,0,255,cv2.NORM_MINMAX,cv2.CV_8UC1)
    out_face2Normal = cv2.normalize(out_face2sub,None,0,255,cv2.NORM_MINMAX,cv2.CV_8UC1)
    out_face3Normal = cv2.normalize(out_face3sub,None,0,255,cv2.NORM_MINMAX,cv2.CV_8UC1)
    #:cv2.imwrite("output2-subNormal2.jpg",out_face2Normal)
#    rows,cols,_= out_face1Normal.shape
#    mogong=0
#    for i in range(rows):
#        for j in range(cols):
#            b,g,r = out_face2Normal[i,j]
#            if b<30 and g<30 and r<30:
#                mogong=mogong+1
                #out_face2Normal[i,j] = [0,0,255]
#    print('/왼쪽 볼 트러블%:', (count/mogong)*100)
#    rows2,cols2,_2= out_face2Normal.shape
#    mogong2=0
#    for i in range(rows2):
#        for j in range(cols2):
#            b,g,r = out_face2Normal[i,j]
#            if b<30 and g<30 and r<30:
#                mogong2=mogong2+1
                #out_face2Normal[i,j] = [0,0,255]
#    print('/코 트러블%:', (count2/mogong2)*100)
    rows3,cols3,_3= out_face3Normal.shape
    mogong3=0
    for i in range(rows3):
        for j in range(cols3):
            b,g,r = out_face2Normal[i,j]
            if b<30 and g<30 and r<30:
                mogong3=mogong3+1
                #out_face2Normal[i,j] = [0,0,255]
    print('/오른쪽 볼 트러블%:', (count3/mogong3)*100)

    #cv2.imwrite("output1-subNormal.jpg",out_face1Normal)
    #cv2.imwrite("output2-subNormal.jpg",out_face2Normal)
    #cv2.imwrite("output3-subNormal.jpg",out_face3Normal)
    
    # N = cv2.normalize(out_face2,None,0,255,cv2.NORM_MINMAX,cv2.CV_8UC1)
    # cv2.imwrite("C:\python_Study\\tensorflow\images\output9.jpg",N)
    # # b,g,r = cv2.split(out_face2)
    # # b_minmax = cv2.normailize
    # # g_minmax = MinMaxScaler(g,feature_range=(0,1))
    # # r_minmax = MinMaxScaler(r,feature_range=(0,1))
    # # diff2 = cv2.merge([r_minmax,g_minmax,b_minmax])
    
    # cv2.imwrite("C:\python_Study\\tensorflow\images\output6.jpg",out_face2)

    # out_face3 = cv2.add(out_face1,N)
    

    # cv2.imwrite("C:\python_Study\\tensorflow\images\output7.jpg",out_face3)
    # out_face2 = cv2.add(out_face2,out_face2)
    # out_face2 = cv2.add(out_face2,out_face2)
    # out_face2 = cv2.add(out_face2,out_face2)
    
    
    # cv2.imwrite("C:\python_Study\\tensorflow\images\output8.jpg",out_face2)
    # cv2.waitKey(0)


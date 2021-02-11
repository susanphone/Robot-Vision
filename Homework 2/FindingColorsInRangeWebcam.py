# Maria Gallivan and Susan McCartney
# CSCI 442: Assignment 2
# February 19, 2021

import cv2 as cv
import time
import numpy as np

global hh
global hv
global hs
global lh
global lv
global ls
hh = hv = hs = lh = lv = ls = 10

# click left mouse button and retrieve color values at specific coordinate
def getValues(event, x, y):
    if event == cv.EVENT_LBUTTONDOWN:  
        colorsH = hsv[y, x, 0]
        colorsS = hsv[y, x, 1]
        colorsV = hsv[y, x, 2]
        colors = hsv[y, x]
        print("Hue: ", colorsH)
        print("Saturation: ", colorsS)
        print("Lightness: ", colorsV)
        print("BRG Format: ", colors)
        print("Coordinates of pixel: X: ", x, "Y: ", y)


def nothing(x):
    pass


# Capture live video from Webcam
cap = cv.VideoCapture(0)
# Display live video
cv.namedWindow("Video")

# Using Sliders create scalers
cv.namedWindow("Trackbars")
cv.createTrackbar("LH", "Trackbars", 0, 179, nothing)
cv.createTrackbar("LS", "Trackbars", 0, 255, nothing)
cv.createTrackbar("LV", "Trackbars", 0, 255, nothing)
cv.createTrackbar("HH", "Trackbars", 179, 179, nothing)
cv.createTrackbar("HS", "Trackbars", 255, 255, nothing)
cv.createTrackbar("HV", "Trackbars", 255, 255, nothing)


while(True):
    
    # Take each frame
    status, src = cap.read()
    
    # Convert BGR to HSV
    hsv = cv.cvtColor(src, cv.COLOR_BGR2HSV)
    
    # Display HSV video
    cv.imshow("Video", hsv)
    
    # Click on HSV video and capture the HSV values at the location clicked
    cv.setMouseCallback("Video", getValues)

    # Using Sliders create scalers for the min and max values you want to track
    lh = cv.getTrackbarPos("LH", "Trackbars")
    ls = cv.getTrackbarPos("LS", "Trackbars")
    lv = cv.getTrackbarPos("LV", "Trackbars")
    hh = cv.getTrackbarPos("HH", "Trackbars")
    hs = cv.getTrackbarPos("HS", "Trackbars")
    hv = cv.getTrackbarPos("HV", "Trackbars")

    min = np.array([lh, ls, lv])
    max = np.array([hh, hs, hv])


    # Use the OpenCV inRange method to find the values between the scalars from HSV image and the result will go to a grayscale image
    mask = cv.inRange(hsv, min, max)

    # Dilate, erode the grayscale image to get a better representation of the object you are tracking.
    # Bitwise-AND mask and original image
    res = cv.bitwise_and(src, src, mask=mask)

    #  The tracked object will be white.
    mask_3 = cv.cvtColor(mask, cv.COLOR_GRAY2BGR)
    stacked = np.hstack((mask_3, src, res))

    # Display the original image and the binary image where everything is black except for the object you are tracking.
    cv.imshow("Original", src)
    cv.imshow("Mask", mask)
    cv.imshow("Res", res)

    # Exit using "q"
    if cv.waitKey(1) & 0xFF == ord("q"):
        cv.destroyAllWindows()
        break

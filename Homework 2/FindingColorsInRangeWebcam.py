import cv2 as cv, time
import numpy as np

global hh
global hv
global hs
global lh
global lv
global ls
hh = hv = hs = lh = lv = ls = 10

# TODO: 1) Capture live video from Webcam
    # https://docs.opencv.org/master/df/d9d/tutorial_py_colorspaces.html
cap = cv.VideoCapture(0)
# TODO: 2) Display live video
while(1):
    # Take each frame
    _, frame = cap.read()
# TODO: 3) Convert BGR to HSV
    hsv = cv.cvtColor(frame, cv.COLOR_BGR2HSV)
# TODO: 4) Display HSV video
# TODO: 5) Click on HSV video and capture the HSV values at the location clicked, and a few other local values of the item you are going to track.
    # define range of blue color in HSV
# TODO: 6) Using Sliders create scalers for the min and max values you want to track
    minH = np.array([])
    minS = np.array([])
    minV = np.array([])
    maxH = np.array([])
    maxS = np.array([])
    maxV = np.array([])
def createTackbar() :
    return
# TODO: 7) Us the OpenCV inRange method to find the values between the scalars from HSV image and the result will go to a grayscale image (make it a binary image, white/black).
    # Threshold the HSV image to get only blue colors
    mask = cv.inRange(hsv, lower_blue, upper_blue)
# TODO: 8) Dilate, erode the grayscale image to get a better representation of the object you are tracking.
    # Bitwise-AND mask and original image
    res = cv.bitwise_and(frame, frame, mask=mask)
# TODO: Display the original image and the binary image where everything is black except for the object you are tracking. The tracked object will be white.
    cv.imshow('frame', frame)
    cv.imshow('mask', mask)
    cv.imshow('res', res)
    k = cv.waitKey(5) & 0xFF
    if k == 27:
        break
cap.release()
cv.destroyAllWindows()

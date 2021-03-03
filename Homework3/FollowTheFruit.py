from controller import *
import cv2 as cv
import numpy as np

# use object tracking from last program
global hh
global hv
global hs
global lh
global lv
global ls
hh = hv = hs = lh = lv = ls = 10


def getValues(event, x, y, flags, param):
    if event == cv.EVENT_LBUTTONDOWN:  # checks mouse left button down condition
        colorsH = hsv[y, x, 0]
        colorsS = hsv[y, x, 1]
        colorsV = hsv[y, x, 2]
        colors = hsv[y, x]
        print("Hue: ", colorsH)
        print("Saturation: ", colorsS)
        print("Lightness: ", colorsV)
        print("BRG Format: ", colors)
        print("Coordinates of pixel: X: ", x, "Y: ", y)
    return


def nothing(x):
    pass

# TODO: capture video from e-puck
cap = cv.VideoCapture(0)
cv.namedWindow("Video")


while(True):

    status, src = cap.read()

    hsv = cv.cvtColor(src, cv.COLOR_BGR2HSV)

    cv.imshow("Video", hsv)

    cv.setMouseCallback("Video", getValues)
# TODO: keep e-puck a set distance away from orange

min = np.array([lh, ls, lv])
max = np.array([hh, hs, hv])

mask = cv.inRange(hsv, min, max)

res = cv.bitwise_and(src, src, mask=mask)

mask_3 = cv.cvtColor(mask, cv.COLOR_GRAY2BGR)
stacked = np.hstack((mask_3, src, res))
cv.imshow("Original", src)
cv.imshow("Mask", mask)
cv.imshow("Res", res)

if cv.waitKey(1) & 0xFF == ord("q"):
    cv.destroyAllWindows()
    break

cap.release()
cv.destroyAllWindows()

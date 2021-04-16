import cv2 as cv
from numpy.lib.twodim_base import eye


src = cap.read("mugshot.jpg")
left = cap.read ("lefteye.jpg")
right = cap.read("righteye.jpg")

dst = cv.cvtColor(dst, gray, cv.COLOR_RGBA2GRAY, 0)
// detect eyes.
classifier.detectMultiScale(gray, eyes, 1.1, 3, 0)
// draw eyes.
for (let i=0
     i < faces.size()
     + +i) {
    let eye = eye.get(i)
    point1 = left
    point2 = right
    let point1 = new cv.Point(eye.x, eye.y)
    let point2 = new cv.Point(eye.x + eye.width, eye.y + eye.height)
    pic = cv.rectangle(dst, point1, point2, [255, 0, 0, 255])
}
source_window = 'Susan McCartney'
cv.FONT_HERSHEY_PLAIN("Ariel")
cv.namedWindow(source_window)
name = "CSCI 442 - Susan McCartney"
cv.imshow(name, pic)

cv.waitKey()

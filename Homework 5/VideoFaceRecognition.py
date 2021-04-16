import pickle
import numpy as np
import cv2 as cv

face_cascade = cv.CascadeClassifier()
recognizer = cv.face.LBPHFaceRocognizer_create()
recognizer.read()
labels = {}
with open ("labels.pickle", 'rb') as f:
    of_labels = pickle.load(f)
    labels = {v:k for k, v in of_labels.items()}

cv.namedWindow("image")
cap = cv.VideoCapture(0)

while True:
    status, img = cap.read()
    gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    # print(faces)
    for (x,y,w,h) in faces:
        cv.rectangle(img, (x,y), (x+w, y+h), (255,0,0),2)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = img[y:y+h, x:x+w]
        id, conf = recognizer.predict(roi_gray)
        # print(conf)
        if conf >= 45 and conf <= 85:
            name = labels[id]
            print(name)
            cv.putText(img, labels[id], (x,y), cv.FONT_HERSHEY_SIMPLEX, 1, (0,0,0), 2, cv.LINE_AA)

            # print("id", id)
            # load the names from pickle
            # print(labels[id])
    cv.imshow("image", img)
    if cv.waitKey(1) & 0xFF == ord('q'):
        break
cv.destroyAllWindows()
cap.release()

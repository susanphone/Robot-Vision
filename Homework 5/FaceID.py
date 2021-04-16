import cv2 as cv
import numpy as np
import pickle

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
image_dir = os.path.join(BASE_DIR, "images")
print(BASE_DIR + '.xml')
face_cascade = cv.CascadeClassifier(BASE_DIR + ".xml")
# recognizer = cv.face.LBPHFaceRecognizer_create()
recognizer = cv.face.LBPHFaceRecognizer_create()
current_id = 0
label_ids = {}
y_labels = []
x_train = []
for root, dirs, files in os.walk(image_dir):
    for file in files:
        if file.endswith("png") or file.endswith("jpg") or file.endswith("jfif"):
            path = os.path.join(root, file)
            label = os.path.basename(root).replace(" ", "_").lower()

            # print(label, path)
            if not label in label_ids:
                label_ids[label] = current_id
                current_id += 1
            id = label_ids[label]
            # print(label_ids)
            # y_label.append(
                pil_image = Image.open(path).convert("L")
                image_array = np.array(pil_image, "uint8")
                # print(image_array)
                faces = face_cascade.detectMultiScale(image_array, 1.3, 5)
                for(x,y,w,h) in faces:
                    roi = image_array[y:y+h, x:x+w]
                    x_train.append(roi)
                    y_labels.append(id)
# print(y_labels)
# print(x_train)
with open("labels.pickle", "wb") as f:
    pickle.dump(label_ids, f)
# train the opencv recognizer
recognizer.train(x_train, np.array(y_labels))
recognizer.save("traner.yml")
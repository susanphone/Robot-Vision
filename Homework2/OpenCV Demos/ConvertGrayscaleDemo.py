import cv2 as cv

src = cv.imread("Homework2/japaneseflowers.jpg", 1)
if src is None:
    print("missing image")
else:
    cv.imshow("windowM", src)
    src = cv.cvtColor(src, cv.COLOR_RGB2GRAY)    
    cv.imshow("windowMy", src)
                
                
                

install.packages("e1071")
# Importing e1071 library for naive bayes classifier
library(e1071)
# Loading the dataset from CSV.
housing <- read.csv("/Users/akshaymathur/Downloads/finalcensus_after_removing_vacanthouses.csv", header = TRUE)
# Arranging the columns in required order for training.
myvars<-c("DIVISION","ACR","BDSP","ACCESS","ELEP","GASP","FINCP","ST","RNTPRange")
# Applying the column format created above.
housing<-housing[myvars]
# randomizing the data and partitioning into training and testing
set.seed(1234)
hous <- sample(2, nrow(housing), replace=TRUE, prob=c(0.80, 0.20))
housing.training<-housing[hous==1,-9]
housing.test<-housing[hous==2,-9]
housing.trainLabels <- housing[hous==1, 9]
housing.testLabels <- housing[hous==2, 9]

# Generating the model using naive bayes
nb<-naiveBayes(housing.training,housing.trainLabels)
# Generating the confusion matrix using the model created above
cm<-table(predict(nb, housing.test), housing.testLabels)
# Calculating the evaluation parameters for the model
print("The accuracy of the model is:")
(accuracy <- sum(diag(cm)) / sum(cm))
print("The precision for each class is:")
(precision <- diag(cm) / rowSums(cm))
recall <- (diag(cm) / colSums(cm))
print("The recall for each class is:")
print(recall)
x<-c(predict(nb, housing.test))
x <- as.factor(x)
levels(x) <- 1:length(levels(x))
x <- as.numeric(x)
y<-c(housing.testLabels)
levels(y) <- 1:length(levels(y))
y <- as.numeric(y)
print("The root mean square error is:")
rmse(y,x)

# Taking the user input for prediction
inputData <- read.csv("/Users/akshaymathur/Downloads/userinput.csv",header = TRUE)
testvars<-c("DIVISION","ACR","BDSP","ACCESS","ELEP","GASP","FINCP","ST")
inputData<-inputData[testvars]
print(inputData)
print("The predicted rent range is:")
print(predict(nb, inputData))
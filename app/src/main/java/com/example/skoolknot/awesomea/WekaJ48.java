package com.example.skoolknot.awesomea;

import android.util.Log;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by skoolknot on 6/11/15.
 */
public class WekaJ48 {
    public class TestingWeka {

        public void test() throws Exception {
            FastVector a1 = new FastVector(3);
            a1.addElement("sunny");
            a1.addElement("overcast");
            a1.addElement("rainy");
            Attribute Attr1 = new Attribute("outlook", a1);

            Attribute Attr2 = new Attribute("temperature");

            Attribute Attr3 = new Attribute("humidity");

            FastVector a4 = new FastVector(2);
            a4.addElement("true");
            a4.addElement("false");
            Attribute Attr4 = new Attribute("windy", a4);

            FastVector a5 = new FastVector(2);
            a5.addElement("yes");
            a5.addElement("no");
            Attribute Attr5 = new Attribute("play", a5);

            FastVector featureVector = new FastVector(5);
            featureVector.addElement(Attr1);
            featureVector.addElement(Attr2);
            featureVector.addElement(Attr3);
            featureVector.addElement(Attr4);
            featureVector.addElement(Attr5);

            Instances isTrainingSet = new Instances("weather", featureVector, 10);
            isTrainingSet.setClassIndex(isTrainingSet.numAttributes() - 1);

            Instance e1 = new Instance(5);
            e1.setValue((Attribute) featureVector.elementAt(0), "sunny");
            e1.setValue((Attribute) featureVector.elementAt(1), 85);
            e1.setValue((Attribute) featureVector.elementAt(2), 85);
            e1.setValue((Attribute) featureVector.elementAt(3), "false");
            e1.setValue((Attribute) featureVector.elementAt(4), "no");

            Instance e2 = new Instance(5);
            e2.setValue((Attribute) featureVector.elementAt(0), "sunny");
            e2.setValue((Attribute) featureVector.elementAt(1), 80);
            e2.setValue((Attribute) featureVector.elementAt(2), 90);
            e2.setValue((Attribute) featureVector.elementAt(3), "true");
            e2.setValue((Attribute) featureVector.elementAt(4), "no");

            Instance e3 = new Instance(5);
            e3.setValue((Attribute) featureVector.elementAt(0), "overcast");
            e3.setValue((Attribute) featureVector.elementAt(1), 83);
            e3.setValue((Attribute) featureVector.elementAt(2), 86);
            e3.setValue((Attribute) featureVector.elementAt(3), "false");
            e3.setValue((Attribute) featureVector.elementAt(4), "yes");

            Instance e4 = new Instance(5);
            e4.setValue((Attribute) featureVector.elementAt(0), "rainy");
            e4.setValue((Attribute) featureVector.elementAt(1), 70);
            e4.setValue((Attribute) featureVector.elementAt(2), 96);
            e4.setValue((Attribute) featureVector.elementAt(3), "false");
            e4.setValue((Attribute) featureVector.elementAt(4), "yes");

            Instance e5 = new Instance(5);
            e5.setValue((Attribute) featureVector.elementAt(0), "rainy");
            e5.setValue((Attribute) featureVector.elementAt(1), 68);
            e5.setValue((Attribute) featureVector.elementAt(2), 80);
            e5.setValue((Attribute) featureVector.elementAt(3), "false");
            e5.setValue((Attribute) featureVector.elementAt(4), "yes");

            isTrainingSet.add(e1);
            isTrainingSet.add(e2);
            isTrainingSet.add(e3);
            isTrainingSet.add(e4);
            isTrainingSet.add(e5);

            J48 model = new J48();
            model.buildClassifier(isTrainingSet);

            Evaluation test = new Evaluation(isTrainingSet);
            test.evaluateModel(model, isTrainingSet);

            Log.d("SUMMARY", "No. of instances: " + isTrainingSet.numInstances());
            Log.d("SUMMARY", "" + model.toString());
        }

    }
}

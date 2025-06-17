package uob.oop;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 3.1 - 0.5 marks
        doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 3.2 - 2 marks
        if (_index>-1 && _index<this.getVectorSize()) {
            return doubElements[_index];
        } else {
            return -1; //you need to modify the return value
        }
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 3.3 - 2 marks
        if (_index>-1 && _index<this.getVectorSize()) {
            doubElements[_index] = _value;
        } else {
            doubElements[this.getVectorSize()-1] = _value;
        }
    }

    public double[] getAllElements() {
        //TODO Task 3.4 - 0.5 marks
        return doubElements; //you need to modify the return value
    }

    public int getVectorSize() {
        //TODO Task 3.5 - 0.5 marks
        return doubElements.length; //you need to modify the return value
    }

    public Vector reSize(int _size) {
        //TODO Task 3.6 - 6 marks
        double[] newElements;
        if (this.getVectorSize() == _size || _size<=0) {
            return this;
        } else if (this.getVectorSize() > _size) {
            newElements = new double[_size];
            for (int i=0; i<_size; i++) {
                newElements[i] = doubElements[i];
            }
            return new Vector(newElements);
        } else {
            newElements = new double[_size];
            for (int i=0; i<this.getVectorSize(); i++) {
                newElements[i] = doubElements[i];
            }
            for (int j=this.getVectorSize(); j<_size; j++) {
                newElements[j] = -1;
            }
            return new Vector(newElements); //you need to modify the return value
        }
    }

    public Vector add(Vector _v) {
        //TODO Task 3.7 - 2 marks
        if (_v.getVectorSize() > this.getVectorSize()) {
            doubElements = reSize(_v.getVectorSize()).getAllElements();
        } else {
            _v = _v.reSize(this.getVectorSize());
        }
        for (int i=0; i<this.getVectorSize(); i++) {
            doubElements[i] = doubElements[i] + _v.getElementatIndex(i);
        }
        return this; //you need to modify the return value
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 3.8 - 2 marks
        if (_v.getVectorSize() > this.getVectorSize()) {
            doubElements = reSize(_v.getVectorSize()).getAllElements();
        } else {
            _v = _v.reSize(this.getVectorSize());
        }
        for (int i=0; i<this.getVectorSize(); i++) {
            doubElements[i] = doubElements[i] - _v.getElementatIndex(i);
        }
        return this; //you need to modify the return value
    }

    public double dotProduct(Vector _v) {
        //TODO Task 3.9 - 2 marks
        if (_v.getVectorSize() > this.getVectorSize()) {
            doubElements = reSize(_v.getVectorSize()).getAllElements();
        } else {
            _v = _v.reSize(this.getVectorSize());
        }
        double dotProduct = 0.0;
        for (int i=0; i< this.getVectorSize(); i++) {
            dotProduct = dotProduct + doubElements[i]*_v.getElementatIndex(i);
        }
        return dotProduct; //you need to modify the return value
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 3.10 - 6.5 marks
        if (_v.getVectorSize() > this.getVectorSize()) {
            doubElements = reSize(_v.getVectorSize()).getAllElements();
        } else {
            _v = _v.reSize(this.getVectorSize());
        }
        double numerator = this.dotProduct(_v);
        double denominator = Math.sqrt(sumOfSquaredElements(_v))*Math.sqrt(sumOfSquaredElements(this));
        return numerator / denominator; //you need to modify the return value
    }

    public double sumOfSquaredElements(Vector _v) {
        double sum = 0.0;
        for (int i=0; i<_v.getVectorSize(); i++) {
            sum = sum + _v.getElementatIndex(i)*_v.getElementatIndex(i);
        }
        return sum;
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;

        if (this.getVectorSize() != v.getVectorSize())
            return false;

        for (int i = 0; i < this.getVectorSize(); i++) {
            if (this.getElementatIndex(i) != v.getElementatIndex(i)) {
                boolEquals = false;
                break;
            }
        }
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}

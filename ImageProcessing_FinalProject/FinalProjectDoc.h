
// FinalProjectDoc.h : CFinalProjectDoc 클래스의 인터페이스
//


#pragma once


class CFinalProjectDoc : public CDocument
{
protected: // serialization에서만 만들어집니다.
	CFinalProjectDoc();
	DECLARE_DYNCREATE(CFinalProjectDoc)

// 특성입니다.
public:

// 작업입니다.
public:

// 재정의입니다.
public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
#ifdef SHARED_HANDLERS
	virtual void InitializeSearchContent();
	virtual void OnDrawThumbnail(CDC& dc, LPRECT lprcBounds);
#endif // SHARED_HANDLERS

// 구현입니다.
public:
	virtual ~CFinalProjectDoc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// 생성된 메시지 맵 함수
protected:
	DECLARE_MESSAGE_MAP()

#ifdef SHARED_HANDLERS
	// 검색 처리기에 대한 검색 콘텐츠를 설정하는 도우미 함수
	void SetSearchContent(const CString& value);
#endif // SHARED_HANDLERS
public:
	virtual BOOL OnOpenDocument(LPCTSTR lpszPathName);
	virtual void OnCloseDocument();
	virtual BOOL OnSaveDocument(LPCTSTR lpszPathName);
	unsigned char**m_InputImage;
	int m_height;
	int m_width;
	unsigned char**m_OutputImage;
	int m_Re_height;
	int m_Re_width;
	afx_msg void OnHistoStretch();
	afx_msg void OnEndInSearch();
	afx_msg void OnHistogram();
	afx_msg void OnHistoEqual();
	afx_msg void OnEmbossing();
	double** OnMaskProcess(double Mask[3][3]);
	afx_msg void OnBlurr();
	double** OnMaskProcess2(double** Mask, int maskSize);
	afx_msg void OnSharpening();
	afx_msg void OnGaussianFilter();
	afx_msg void OnHpfSharp();
	afx_msg void OnLpfSharp();
	afx_msg void OnDiffOperator();
	afx_msg void OnHomogenOperator();
	double FindMaxValue(double** InImage, int x, int y);
	afx_msg void OnChaOperator();
	afx_msg void OnLog();
	double FindMaxValueCha(double** InImage, int x, int y);
	afx_msg void OnDog();
	afx_msg void OnZoomIn();
	afx_msg void OnNearest();
	afx_msg void OnBilinear();
	afx_msg void OnZoomOut();
	afx_msg void OnMedianSub();
	unsigned char OnFindMedian(double** Mask, int mSize);
	afx_msg void OnSub();
	unsigned char OnFindAVG(double** Mask, int mSize);
	afx_msg void OnTranslation();
	afx_msg void OnMirrorHor();
	afx_msg void OnMirrorVer();
	afx_msg void OnRotation();
	afx_msg void OnMeanFilter();
	double FindAVGValue(double** InImage, int x, int y);
	afx_msg void OnMedianFilter();
	double FindMIDValue(double** InImage, int x, int y);
	afx_msg void OnMaxFilter();
	double FindMAXValue2(double** InImage, int x, int y);
	afx_msg void OnMinFilter();
	double FindMINValue2(double** InImage, int x, int y);
	afx_msg void OnZoomInC();
	unsigned char**m_InputImageR;
	unsigned char**m_InputImageG;
	unsigned char**m_InputImageB;
	unsigned char**m_OutputImageR;
	unsigned char**m_OutputImageG;
	unsigned char**m_OutputImageB;
};

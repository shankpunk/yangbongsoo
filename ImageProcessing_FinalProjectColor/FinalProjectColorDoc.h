
// FinalProjectColorDoc.h : CFinalProjectColorDoc Ŭ������ �������̽�
//


#pragma once


class CFinalProjectColorDoc : public CDocument
{
protected: // serialization������ ��������ϴ�.
	CFinalProjectColorDoc();
	DECLARE_DYNCREATE(CFinalProjectColorDoc)

// Ư���Դϴ�.
public:

// �۾��Դϴ�.
public:

// �������Դϴ�.
public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
#ifdef SHARED_HANDLERS
	virtual void InitializeSearchContent();
	virtual void OnDrawThumbnail(CDC& dc, LPRECT lprcBounds);
#endif // SHARED_HANDLERS

// �����Դϴ�.
public:
	virtual ~CFinalProjectColorDoc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// ������ �޽��� �� �Լ�
protected:
	DECLARE_MESSAGE_MAP()

#ifdef SHARED_HANDLERS
	// �˻� ó���⿡ ���� �˻� �������� �����ϴ� ����� �Լ�
	void SetSearchContent(const CString& value);
#endif // SHARED_HANDLERS
public:
	virtual void OnCloseDocument();
	virtual BOOL OnOpenDocument(LPCTSTR lpszPathName);
	unsigned char**m_InputImageR;
	unsigned char**m_InputImageG;
	unsigned char**m_InputImageB;
	unsigned char**m_OutputImageR;
	unsigned char**m_OutputImageG;
	unsigned char**m_OutputImageB;
	int m_width;
	int m_height;
	int m_Re_width;
	int m_Re_height;
	afx_msg void OnZoomIn();
	afx_msg void OnNearest();
	afx_msg void OnBilinear();
	afx_msg void OnZoomOut();
	afx_msg void OnMedianSub();
	unsigned char OnFindMedian(double** Mask, int mSize);
	afx_msg void OnMeanSub();
	unsigned char OnFindAVG(double** Mask, int mSize);
	afx_msg void OnTranslation();
	afx_msg void OnMirrorHor();
	afx_msg void OnMirrorVer();
	afx_msg void OnRotation();
};

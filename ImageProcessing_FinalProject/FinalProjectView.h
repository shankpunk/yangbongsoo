
// FinalProjectView.h : CFinalProjectView Ŭ������ �������̽�
//

#pragma once


class CFinalProjectView : public CView
{
protected: // serialization������ ��������ϴ�.
	CFinalProjectView();
	DECLARE_DYNCREATE(CFinalProjectView)

// Ư���Դϴ�.
public:
	CFinalProjectDoc* GetDocument() const;

// �۾��Դϴ�.
public:

// �������Դϴ�.
public:
	virtual void OnDraw(CDC* pDC);  // �� �並 �׸��� ���� �����ǵǾ����ϴ�.
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);

// �����Դϴ�.
public:
	virtual ~CFinalProjectView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// ������ �޽��� �� �Լ�
protected:
	afx_msg void OnFilePrintPreview();
	afx_msg void OnRButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnContextMenu(CWnd* pWnd, CPoint point);
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnHistoStretch();
	afx_msg void OnEndInSearch();
	afx_msg void OnHistogram();
	afx_msg void OnHistoEqual();
	afx_msg void OnEmbossing();
	afx_msg void OnBlurr();
	afx_msg void OnSharpening();
	afx_msg void OnGaussianFilter();
	afx_msg void OnHpfSharp();
	afx_msg void OnLpfSharp();
	afx_msg void OnDiffOperator();
	afx_msg void OnHomogenOperator();
	afx_msg void OnChaOperator();
	afx_msg void OnLog();
	afx_msg void OnDog();
	afx_msg void OnZoomIn();
	afx_msg void OnNearest();
	afx_msg void OnBilinear();
	afx_msg void OnZoomOut();
	afx_msg void OnMedianSub();
	afx_msg void OnSub();
	afx_msg void OnTranslation();
	afx_msg void OnMirrorHor();
	afx_msg void OnMirrorVer();
	afx_msg void OnRotation();
	afx_msg void OnMeanFilter();
	afx_msg void OnMedianFilter();
	afx_msg void OnMaxFilter();
	afx_msg void OnMinFilter();
	afx_msg void OnZoomInC();
};

#ifndef _DEBUG  // FinalProjectView.cpp�� ����� ����
inline CFinalProjectDoc* CFinalProjectView::GetDocument() const
   { return reinterpret_cast<CFinalProjectDoc*>(m_pDocument); }
#endif


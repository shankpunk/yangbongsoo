
// FinalProjectView.h : CFinalProjectView 클래스의 인터페이스
//

#pragma once


class CFinalProjectView : public CView
{
protected: // serialization에서만 만들어집니다.
	CFinalProjectView();
	DECLARE_DYNCREATE(CFinalProjectView)

// 특성입니다.
public:
	CFinalProjectDoc* GetDocument() const;

// 작업입니다.
public:

// 재정의입니다.
public:
	virtual void OnDraw(CDC* pDC);  // 이 뷰를 그리기 위해 재정의되었습니다.
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);

// 구현입니다.
public:
	virtual ~CFinalProjectView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// 생성된 메시지 맵 함수
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

#ifndef _DEBUG  // FinalProjectView.cpp의 디버그 버전
inline CFinalProjectDoc* CFinalProjectView::GetDocument() const
   { return reinterpret_cast<CFinalProjectDoc*>(m_pDocument); }
#endif


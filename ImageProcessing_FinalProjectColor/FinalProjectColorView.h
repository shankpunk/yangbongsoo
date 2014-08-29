
// FinalProjectColorView.h : CFinalProjectColorView Ŭ������ �������̽�
//

#pragma once


class CFinalProjectColorView : public CView
{
protected: // serialization������ ��������ϴ�.
	CFinalProjectColorView();
	DECLARE_DYNCREATE(CFinalProjectColorView)

// Ư���Դϴ�.
public:
	CFinalProjectColorDoc* GetDocument() const;

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
	virtual ~CFinalProjectColorView();
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
	afx_msg void OnZoomIn();
	afx_msg void OnNearest();
	afx_msg void OnBilinear();
	afx_msg void OnZoomOut();
	afx_msg void OnMedianSub();
	afx_msg void OnMeanSub();
	afx_msg void OnTranslation();
	afx_msg void OnMirrorHor();
	afx_msg void OnMirrorVer();
	afx_msg void OnRotation();
};

#ifndef _DEBUG  // FinalProjectColorView.cpp�� ����� ����
inline CFinalProjectColorDoc* CFinalProjectColorView::GetDocument() const
   { return reinterpret_cast<CFinalProjectColorDoc*>(m_pDocument); }
#endif

